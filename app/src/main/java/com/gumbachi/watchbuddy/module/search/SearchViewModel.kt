package com.gumbachi.watchbuddy.module.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gumbachi.watchbuddy.model.MediaFilter
import com.gumbachi.watchbuddy.model.RecentSearch
import com.gumbachi.watchbuddy.model.UserSettings
import com.gumbachi.watchbuddy.model.WatchbuddyID
import com.gumbachi.watchbuddy.model.interfaces.Editable
import com.gumbachi.watchbuddy.model.interfaces.Media
import com.gumbachi.watchbuddy.model.interfaces.SearchResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

private const val TAG = "SearchViewModel"

data class SearchState(
    val query: String,
    val results: List<SearchResult>
)

data class SearchScreenUiState(
    val loading: Boolean = false,
    val error: Throwable? = null,
    val warning: Throwable? = null,

    val searchState: SearchState? = null,

    val recentSearches: List<RecentSearch> = emptyList(),

    val showFilterDialog: Boolean = false,
    val filter: MediaFilter = MediaFilter(),

    val settings: UserSettings = UserSettings(),

    val underEdit: Media? = null,

    val savedMediaIDs: List<WatchbuddyID> = emptyList()

)

class SearchViewModel(private val repository: SearchRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchScreenUiState())
    val uiState = _uiState.asStateFlow()

    init {
        Log.d(TAG, "Search View Model Created")
        viewModelScope.launch {
            launch {
                repository.getUserSettingsFlow().collectLatest { settings ->
                    _uiState.update { it.copy(settings = settings) }
                }
            }
            launch {
                repository.getSavedMediaIDs().collectLatest { ids ->
                    _uiState.update { it.copy(savedMediaIDs = ids) }
                }
            }
        }
    }

    //region Filter State
    fun showFilterDialog() {
        _uiState.update { it.copy(showFilterDialog = true) }
    }

    fun hideFilterDialog() {
        _uiState.update { it.copy(showFilterDialog = false) }
    }

    fun onFilterUpdate(filter: MediaFilter) {
        _uiState.update { it.copy(filter = filter, showFilterDialog = false) }
    }
    //endregion

    // Search Functions
    fun searchFor(query: String) {
        _uiState.update { it.copy(loading = true) }
        viewModelScope.launch {
            runCatching {
                repository.searchFor(query = query)
            }.onSuccess { results ->
                _uiState.update {
                    it.copy(
                        loading = false,
                        searchState = SearchState(
                            query = query,
                            results = results // TODO Filter this
                        )
                    )
                }
            }.onFailure { error ->
                Log.e(TAG, "Search for '$query' failed: $error")
                error.printStackTrace()
                _uiState.update { it.copy(loading = false, error = error) }
            }
        }
    }

    fun showEditDialogFor(searchResult: SearchResult) {
        viewModelScope.launch {
            val media = repository.findMedia(searchResult)
            _uiState.update {
                it.copy(underEdit = media?.clone() ?: repository.generateBlankMedia(searchResult))
            }
        }
    }

    fun hideEditDialog() {
        _uiState.update { it.copy(underEdit = null) }
    }

    fun saveMedia(
        editable: Editable,
        onSuccess: suspend () -> Unit = {}
    ) {
        hideEditDialog()
        viewModelScope.launch {
            runCatching {
                repository.saveMedia(editable as Media)
            }.onSuccess {
                onSuccess()
            }.onFailure { error ->
                throw error //TODO handle this
            }
        }
    }

    fun updateMedia(
        editable: Editable,
        onSuccess: suspend () -> Unit = {}
    ) {
        hideEditDialog()
        viewModelScope.launch {
            runCatching {
                repository.updateMedia(editable as Media)
            }.onSuccess {
                onSuccess()
            }.onFailure { error ->
                throw error //TODO handle this
            }
        }
    }

    fun deleteMedia(
        editable: Editable,
        onSuccess: suspend () -> Unit = {}
    ) {
        hideEditDialog()
        viewModelScope.launch {
            runCatching {
                repository.deleteMedia(editable as Media)
            }.onSuccess {
                onSuccess()
            }.onFailure { error ->
                throw error //TODO handle this
            }
        }
    }
}
