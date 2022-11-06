package com.gumbachi.watchbuddy.ui.screens.search

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gumbachi.watchbuddy.data.local.SettingsRepository
import com.gumbachi.watchbuddy.data.remote.tmdb.TMDBApi
import com.gumbachi.watchbuddy.data.remote.tmdb.mappers.toTMDBSearchResults
import com.gumbachi.watchbuddy.model.enums.CardStyle
import com.gumbachi.watchbuddy.model.enums.ScoreFormat
import com.gumbachi.watchbuddy.model.interfaces.SearchResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

data class FilterState(
    val TMDB: Boolean = true,
    val anilist: Boolean = true,
    val movies: Boolean = true,
    val shows: Boolean = true
)

data class SearchUiState(
    val loading: Boolean = false,
    val currentSearch: String? = null,
    val searchResults: List<SearchResult>? = null,
    val showFilters: Boolean = false,
    val filterState: FilterState = FilterState(),

    val cardSize: CardStyle = CardStyle.Normal,
    val scoreFormat: ScoreFormat = ScoreFormat.Decimal
)


@HiltViewModel
class SearchViewModel @Inject constructor(
    private val TMDBApi: TMDBApi,
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    var state by mutableStateOf(SearchUiState())
        private set

    fun onFilterButtonClick(newState: Boolean) {
        state = state.copy(showFilters = newState)
    }

    fun updateFilterState(newState: FilterState) {
        state = state.copy(filterState = newState)
    }

    fun updateUserSettings() {
        viewModelScope.launch {
            val settings = settingsRepository.getUserSettings()
            state = state.copy(
                cardSize = settings.cardStyle,
                scoreFormat = settings.scoreFormat
            )
        }
    }

    fun onSearch(query: String) {
        viewModelScope.launch {
            state = state.copy(loading = true)
            val movieResults = TMDBApi.searchMovies(query = query).toTMDBSearchResults()
            val showResults = TMDBApi.searchShows(query = query).toTMDBSearchResults()
            val results = (movieResults + showResults).sortedByDescending { it.weight() }
            results.forEach { Log.d("TMDB", "$it") }
            state = state.copy(loading = false, searchResults = results)
        }
    }

    init {
        Log.d("VM", "Search VM Created")
        updateUserSettings()
    }
}
