package com.gumbachi.watchbuddy.module.shows

import android.util.Log
import androidx.compose.material3.SnackbarHostState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gumbachi.watchbuddy.model.UserSettings
import com.gumbachi.watchbuddy.model.Watchlist
import com.gumbachi.watchbuddy.model.enums.configuration.Sort
import com.gumbachi.watchbuddy.model.enums.data.WatchStatus
import com.gumbachi.watchbuddy.model.interfaces.Show
import com.gumbachi.watchbuddy.utils.displaySnackbar
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.withIndex
import kotlinx.coroutines.launch

private const val TAG = "ShowsViewModel"

data class ShowsScreenUiState(
    val loading: Boolean = false,
    val error: Throwable? = null,

    val watchlist: Watchlist<Show> = Watchlist(),

    val selectedTab: Int = 0,

    val showUnderEdit: Show? = null,

    val showSortDialog: Boolean = false,
    val settings: UserSettings = UserSettings(),
) {

    val shownTabs: List<WatchStatus>
        get() = WatchStatus.values().toList() - settings.hiddenShowStatuses

    val currentList: List<Show>
        get() = watchlist.getListFor(shownTabs[selectedTab])

}

class ShowsViewModel(private val repository: ShowsRepository) : ViewModel() {

    // UI State Flow
    private val _uiState = MutableStateFlow(ShowsScreenUiState())
    val uiState = _uiState.asStateFlow()

    init {
        Log.d(TAG, "Shows View Model Created")
        viewModelScope.launch {
            launch {
                repository.getSettingsFlow().withIndex().collectLatest { (index, settings) ->
                    Log.d(TAG, "Collected Settings State Change")

                    // Reset selected tab if tab layout changes to avoid index problems
                    if (uiState.value.settings.hiddenMovieStatuses != settings.hiddenMovieStatuses) {
                        _uiState.update { it.copy(selectedTab = 0) }
                    }

                    // Only dismiss initial load
                    when (index) {
                        0 -> _uiState.update { it.copy(settings = settings, loading = false) }
                        else -> _uiState.update { it.copy(settings = settings) }
                    }

                }
            }
            launch {
                repository.getWatchListFlow().collectLatest { watchlist ->
                    Log.d(TAG, "Collected Watchlist State Change")
                    _uiState.update { it.copy(watchlist = watchlist) }
                }
            }

        }
    }

    fun acknowledgeError() {
        _uiState.update { it.copy(error = null) }
    }

    fun displayError(error: Throwable) {
        _uiState.update { it.copy(error = error) }
    }

    fun changeWatchStatusTab(selected: Int) {
        _uiState.update { it.copy(selectedTab = selected) }
    }

    // Edit Dialog State
    fun showEditDialogFor(show: Show) {
        _uiState.update { it.copy(showUnderEdit = show.clone()) }
    }

    fun hideEditDialog() {
        _uiState.update { it.copy(showUnderEdit = null) }
    }

    fun deleteShow(snackbarState: SnackbarHostState) {
        val showToDelete = uiState.value.showUnderEdit ?: return
        hideEditDialog()

        viewModelScope.launch {
            runCatching {
                repository.removeShow(showToDelete)
            }.onSuccess {
                displaySnackbar(
                    message = "Show Deleted",
                    actionLabel = "UNDO",
                    snackbarState = snackbarState
                ) {
                    runCatching {
                        repository.addShow(showToDelete)
                    }.onFailure { error ->
                        Log.e(TAG, "Couldn't Undo Show Delete: $error")
                        displayError(error)
                    }
                }
            }.onFailure { error ->
                Log.e(TAG, "Couldn't Delete Show: $error")
                displayError(error)
            }
        }
    }

    fun updateShow(original: Show, updated: Show,  snackbarState: SnackbarHostState) {
        hideEditDialog()
        viewModelScope.launch {
            runCatching {
                repository.updateShowTo(updated)
            }.onSuccess {
                displaySnackbar(
                    message = "Show Updated",
                    actionLabel = "UNDO",
                    snackbarState = snackbarState,
                ) {
                    runCatching {
                        repository.updateShowTo(original)
                    }.onFailure { error ->
                        Log.e(TAG, "Couldn't Undo Movie Update: $error")
                        displayError(error)
                    }
                }
            }.onFailure { error ->
                Log.e(TAG, "Couldn't Update Movie: $error")
                displayError(error)
            }
        }
    }

    // Sort Dialog State
    fun showSortDialog() {
        _uiState.update { it.copy(showSortDialog = true) }
    }

    fun hideSortDialog() {
        _uiState.update { it.copy(showSortDialog = false) }
    }

    fun updateShowSortTo(new: Sort) {
        hideSortDialog()
        viewModelScope.launch {
            runCatching {
                repository.updateShowSortTo(new)
            }.onSuccess {
                _uiState.update { it.copy(watchlist = it.watchlist.copy(sort = new)) }
            }.onFailure { error ->
                Log.d(TAG, "Failed to update Show Sort method $error")
                displayError(error)
            }
        }
    }
}