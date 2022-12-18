package com.gumbachi.watchbuddy.module.movies

import android.util.Log
import androidx.compose.material3.SnackbarHostState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gumbachi.watchbuddy.model.MediaFilter
import com.gumbachi.watchbuddy.model.UserSettings
import com.gumbachi.watchbuddy.model.WatchbuddyID
import com.gumbachi.watchbuddy.model.Watchlist
import com.gumbachi.watchbuddy.model.enums.configuration.Sort
import com.gumbachi.watchbuddy.model.enums.data.WatchStatus
import com.gumbachi.watchbuddy.model.interfaces.Movie
import com.gumbachi.watchbuddy.utils.displaySnackbar
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.withIndex
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
        get() = WatchStatus.values().toList() - settings.hiddenMovieStatuses

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
        val sortFlow = uiState.map { it.settings.movieSort }
        val filterFlow = uiState.map { it.filter.predicate }
        val watchlistFlow = combine(moviesFlow, sortFlow, filterFlow) { movies, sort, filter ->
            Watchlist(
                entries = movies,
                sort = sort,
                filter = filter
            )
        }

        watchlistFlow.collectLatest { watchlist ->
            Log.d(TAG, "Collected Watchlist State Change")
            _uiState.update { it.copy(watchlist = watchlist) }
        }
    }

    private suspend fun beginCollectingSettings() {
        delay(1000L)
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

    fun deleteMovie(snackbarState: SnackbarHostState) {
        val movieToDelete = uiState.value.movieUnderEdit ?: return
        hideEditDialog()
        viewModelScope.launch {
            runCatching {
                repository.removeMovie(movieToDelete)
            }.onSuccess {
                displaySnackbar(
                    message = "Deleted Movie",
                    actionLabel = "UNDO",
                    snackbarState = snackbarState,
                ) {
                    runCatching {
                        repository.addMovie(movieToDelete)
                    }.onFailure { error ->
                        Log.e(TAG, "Couldn't Undo Movie Delete: $error")
                        displayError(error)
                    }
                }
            }.onFailure { error ->
                Log.e(TAG, "Couldn't Delete Movie: $error")
                displayError(error)
            }
        }
    }

    fun updateMovie(original: Movie, updated: Movie, snackbarState: SnackbarHostState) {
        hideEditDialog()
        viewModelScope.launch {
            runCatching {
                repository.updateMovieTo(updated)
            }.onSuccess {
                displaySnackbar(
                    message = "Movie Updated",
                    actionLabel = "UNDO",
                    snackbarState = snackbarState,
                ) {
                    runCatching {
                        repository.updateMovieTo(original)
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


    //region Sort Dialog Functions
    fun showSortDialog() {
        _uiState.update { it.copy(showSortDialog = true) }
    }

    fun hideSortDialog() {
        _uiState.update { it.copy(showSortDialog = false) }
    }

    fun updateMovieSortTo(new: Sort) {
        hideSortDialog()
        viewModelScope.launch {
            runCatching {
                repository.updateMovieSortTo(new)
            }.onSuccess {
                _uiState.update { it.copy(watchlist = it.watchlist.copy(sort = new)) }
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

    fun changeFilterTo(filter: MediaFilter) {
        viewModelScope.launch {
            hideFilterDialog()
            _uiState.update { it.copy(filter = filter) }
        }
    }
    //endregion

    fun findItemOnScreen(
        id: WatchbuddyID,
        attempts: Int = 5,
        delay: Long = 500L,
        onFind: suspend (itemIndex: Int, tab: WatchStatus) -> Unit = { _, _ -> }
    ) {
        viewModelScope.launch {
            for (attempt in 1..attempts) {
                Log.d(TAG, "Attempt #$attempt")
                if (uiState.value.watchlist.contains(id)) {
                    Log.d(TAG, "Item found running onfind")
                    uiState.value.watchlist.findByID(id)?.let { movie ->
                        onFind(
                            uiState.value.watchlist.getListFor(movie.watchStatus)
                                .indexOf(movie),
                            movie.watchStatus
                        )
                    }
                    break
                }
                delay(delay)
            }
        }
    }

}