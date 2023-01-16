package com.gumbachi.watchbuddy.module.search.mediasearch

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gumbachi.watchbuddy.model.MediaFilter
import com.gumbachi.watchbuddy.model.WatchBuddyID
import com.gumbachi.watchbuddy.model.interfaces.Cardable
import com.gumbachi.watchbuddy.model.interfaces.Media
import com.gumbachi.watchbuddy.model.interfaces.SearchResult
import com.gumbachi.watchbuddy.model.settings.UserSettings
import com.gumbachi.watchbuddy.module.search.SearchRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

private const val TAG = "MediaSearchViewModel"

data class CurrentSearch(
    val query: String,
    val results: List<SearchResult>
)

data class QueryScreenUiState(
    val loading: Boolean = false,
    val error: Throwable? = null,

    val searchState: CurrentSearch? = null,

    val showFilterDialog: Boolean = false,
    val filter: MediaFilter = MediaFilter(),

    val settings: UserSettings = UserSettings(),

    val underEdit: Media? = null,
    val savedMediaIDs: List<WatchBuddyID> = emptyList(),

    val showQueryScreen: Boolean = true
)

class MediaSearchViewModel(private val repository: SearchRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(QueryScreenUiState())
    val uiState = _uiState.asStateFlow()

    init {
        Log.d(TAG, "MediaSearch View Model Created")
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

    fun updateFilter(filter: MediaFilter) {
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
                        searchState = CurrentSearch(
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

    fun saveMedia(media: Media) {
        hideEditDialog()
        viewModelScope.launch {
            runCatching {
                repository.saveMedia(media)
            }.onFailure { error ->
                throw error //TODO handle this
            }
        }
    }

    fun updateMedia(media: Media) {
        hideEditDialog()
        viewModelScope.launch {
            runCatching {
                repository.updateMedia(media)
            }.onFailure { error ->
                throw error //TODO handle this
            }
        }
    }

    fun deleteMedia(media: Media) {
        hideEditDialog()
        viewModelScope.launch {
            runCatching {
                repository.deleteMedia(media)
            }.onFailure { error ->
                throw error //TODO handle this
            }
        }
    }

    fun addItemToRecents(item: Cardable) {
        viewModelScope.launch {
            repository.addToRecents(item)
        }
    }
}
