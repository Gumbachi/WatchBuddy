package com.gumbachi.watchbuddy.ui.screens.search

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gumbachi.watchbuddy.data.repository.MovieRepository
import com.gumbachi.watchbuddy.data.repository.SearchRepository
import com.gumbachi.watchbuddy.data.repository.SettingsRepository
import com.gumbachi.watchbuddy.model.EditableState
import com.gumbachi.watchbuddy.model.RecentSearch
import com.gumbachi.watchbuddy.model.SearchFilter
import com.gumbachi.watchbuddy.model.UserSettings
import com.gumbachi.watchbuddy.model.interfaces.SearchResult
import com.gumbachi.watchbuddy.model.tmdb.TMDBMovie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "SearchViewModel"

data class SearchUiState(
    val loading: Boolean = false,
    val error: Throwable? = null,
    val warning: Throwable? = null,

    val currentSearch: String? = null,
    val searchResults: List<SearchResult> = emptyList(),
    val recentSearches: List<RecentSearch> = emptyList(),

    val showFilterDialog: Boolean = false,
    val filter: SearchFilter = SearchFilter(),

    val settings: UserSettings = UserSettings(),

    val underEdit: SearchResult? = null

)


@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchRepository: SearchRepository,
    private val movieRepository: MovieRepository,
    private val settingsRepository: SettingsRepository,
) : ViewModel() {

    var state by mutableStateOf(SearchUiState())
        private set

    init {
        Log.d(TAG, "Search View Model Created")
        viewModelScope.launch {
            settingsRepository.getUserSettingsFlow().collect {
                state = state.copy(settings = it)
            }
        }
    }

    // Filter State
    fun showFilterDialog() {
        state = state.copy(showFilterDialog = true)
    }

    fun hideFilterDialog() {
        state = state.copy(showFilterDialog = false)
    }

    fun onFilterUpdate(filter: SearchFilter) {
        state = state.copy(filter = filter)
    }

    // Search Functions
    fun onSearch(query: String) {
        viewModelScope.launch {
            state = state.copy(loading = true)

            searchRepository.searchFiltered(
                query = query,
                filter = state.filter
            ).onSuccess {
                state = state.copy(
                    loading = false,
                    searchResults = it,
                    currentSearch = query
                )
            }.onFailure {
                Log.e(TAG, "Search for '$query' failed: $it")
                it.printStackTrace()
                state = state.copy(
                    loading = false,
                    error = it,
                )
            }
        }
    }

    fun showEditDialogFor(searchResult: SearchResult) {
        state = state.copy(underEdit = searchResult)
    }

    fun hideEditDialog() {
        state = state.copy(underEdit = null)
    }

    fun onMediaSave(
        searchResult: SearchResult,
        edits: EditableState,
        snackbarAction: () -> Unit = {}
    ) {
        viewModelScope.launch {
            searchRepository.generateBlankMedia(searchResult)
                .onSuccess {
                    when (it) {
                        is TMDBMovie -> {
                            movieRepository.addMovie(it with edits)
                                .onSuccess { snackbarAction() }
                        }

                        else -> TODO("Need to implement other branches")
                    }

                }
                .onFailure {
                    throw it //TODO
                }
        }
    }
}
