package com.gumbachi.watchbuddy.module.movies

import android.util.Log
import androidx.compose.material3.SnackbarHostState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gumbachi.watchbuddy.model.MediaFilter
import com.gumbachi.watchbuddy.model.settings.UserSettings
import com.gumbachi.watchbuddy.model.WatchBuddyID
import com.gumbachi.watchbuddy.model.Watchlist
import com.gumbachi.watchbuddy.model.enums.configuration.Sort
import com.gumbachi.watchbuddy.model.enums.configuration.SortOrder
import com.gumbachi.watchbuddy.model.enums.data.WatchStatus
import com.gumbachi.watchbuddy.model.interfaces.Movie
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

private const val TAG = "MoviesViewModel"

data class MoviesScreenUiState(
    val loading: Boolean = false,
    val error: Throwable? = null,

    val watchlist: Watchlist<Movie> = Watchlist(),

    val selectedTab: Int = 0,

    val movieUnderEdit: Movie? = null,

    val showSortDialog: Boolean = false,
    val showFilterDialog: Boolean = false,

    val settings: UserSettings = UserSettings(),
    val filter: MediaFilter = MediaFilter()
) {

    val shownTabs: List<WatchStatus>
        get() = WatchStatus.values().toList() - settings.movies.hiddenStatuses

    val currentList: List<Movie>
        get() = watchlist.getListFor(shownTabs[selectedTab])

}

class MoviesViewModel(private val repository: MoviesRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(MoviesScreenUiState())
    val uiState = _uiState.asStateFlow()

    init {
        Log.d(TAG, "Movies View Model Created")
        _uiState.update { it.copy(loading = true) }
        viewModelScope.launch {
            launch { beginCollectingSettings() }
            launch { beginCollectingWatchlist() }
        }
    }

    //region State Collectors
    private suspend fun beginCollectingWatchlist() {
        val moviesFlow = repository.getMoviesFlow()
        val sortFlow = uiState.map { it.settings.movies.sort }
        val orderFlow = uiState.map { it.settings.movies.sortOrder }
        val filterFlow = uiState.map { it.filter.predicate }
        val watchlistFlow =
            combine(moviesFlow, sortFlow, orderFlow, filterFlow) { movies, sort, order, filter ->
                Watchlist(
                    entries = movies,
                    sort = sort,
                    order = order,
                    filter = filter
                )
            }

        watchlistFlow.distinctUntilChanged().collectLatest { watchlist ->
            Log.d(TAG, "Collected Watchlist State Change")
            _uiState.update { it.copy(watchlist = watchlist) }
        }
    }

    private suspend fun beginCollectingSettings() {
        repository.getSettingsFlow().withIndex().collectLatest { (index, settings) ->
            Log.d(TAG, "Collected Settings State Change")

            // Reset selected tab if tab layout changes to avoid index problems
            if (uiState.value.settings.movies.hiddenStatuses != settings.movies.hiddenStatuses) {
                _uiState.update { it.copy(selectedTab = 0) }
            }

            // Only dismiss initial load
            when (index) {
                0 -> _uiState.update { it.copy(settings = settings, loading = false) }
                else -> _uiState.update { it.copy(settings = settings) }
            }

        }
    }
    //endregion

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
    fun showEditDialogFor(movie: Movie) {
        _uiState.update { it.copy(movieUnderEdit = movie.clone()) }
    }

    fun hideEditDialog() {
        _uiState.update { it.copy(movieUnderEdit = null) }
    }

    fun deleteMovie(movie: Movie) {
        hideEditDialog()
        viewModelScope.launch {
            runCatching {
                repository.removeMovie(movie)
            }.onFailure { error ->
                Log.e(TAG, "Couldn't Delete ${movie.title}: $error")
                displayError(error)
            }
        }
    }

    fun updateMovie(updated: Movie) {
        hideEditDialog()
        viewModelScope.launch {
            runCatching {
                repository.updateMovieTo(updated)
            }.onFailure { error ->
                Log.e(TAG, "Couldn't Update Movie: $error")
                displayError(error)
            }
        }
    }

    fun undoDelete(movie: Movie) {
        viewModelScope.launch {
            runCatching {
                repository.addMovie(movie)
            }.onFailure { error ->
                Log.e(TAG, "Couldn't Save Movie: $error")
                displayError(error)
            }
        }
    }

    //region Sort Dialog Functions
    fun showSortDialog() {
        _uiState.update { it.copy(showSortDialog = true) }
    }

    fun hideSortDialog() {
        _uiState.update { it.copy(showSortDialog = false) }
    }

    fun updateMovieSort(sort: Sort, order: SortOrder) {
        hideSortDialog()
        viewModelScope.launch {
            runCatching {
                repository.updateMovieSortTo(sort, order)
            }.onSuccess {
                _uiState.update { it.copy(watchlist = it.watchlist.copy(sort = sort)) }
            }.onFailure { error ->
                Log.d(TAG, "Failed to update sort method $error")
                displayError(error)
            }
        }
    }
    //endregion

    //region Filter Dialog Functions
    fun showFilterDialog() {
        _uiState.update { it.copy(showFilterDialog = true) }
    }

    fun hideFilterDialog() {
        _uiState.update { it.copy(showFilterDialog = false) }
    }

    fun updateFilter(filter: MediaFilter) {
        viewModelScope.launch {
            hideFilterDialog()
            _uiState.update { it.copy(filter = filter) }
        }
    }
    //endregion

    suspend fun findMovie(id: WatchBuddyID) =
        repository.getMoviesFlow().first().find { it.watchbuddyID == id }

}