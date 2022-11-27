package com.gumbachi.watchbuddy.ui.screens.movies

import android.util.Log
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gumbachi.watchbuddy.data.repository.MovieRepository
import com.gumbachi.watchbuddy.data.repository.SettingsRepository
import com.gumbachi.watchbuddy.model.EditableState
import com.gumbachi.watchbuddy.model.UserSettings
import com.gumbachi.watchbuddy.model.Watchlist
import com.gumbachi.watchbuddy.model.enums.configuration.Sort
import com.gumbachi.watchbuddy.model.enums.data.WatchStatus
import com.gumbachi.watchbuddy.model.interfaces.Movie
import com.gumbachi.watchbuddy.utils.displaySnackbar
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "MoviesViewModel"

data class MoviesScreenUiState(

    val loading: Boolean = false,
    val error: Throwable? = null,

    val watchlist: Watchlist<Movie> = Watchlist(),

    val currentTab: WatchStatus = WatchStatus.Watching,

    val movieUnderEdit: Movie? = null,

    val showSortDialog: Boolean = false,
    val settings: UserSettings = UserSettings(),
) {

    val currentStatus: WatchStatus get() = currentTab
    val currentList: List<Movie> get() = watchlist.getListFor(currentStatus)

}

@HiltViewModel
class MoviesScreenViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    var state by mutableStateOf(MoviesScreenUiState())
        private set

    init {
        Log.d(TAG, "Movies View Model Created")
        viewModelScope.launch {

            val watchlistFlow = movieRepository.getWatchList(Sort.ScoreDescending)
            val settingsFlow = settingsRepository.getUserSettingsFlow()

            watchlistFlow.combine(settingsFlow) { w, s -> w to s }.collect {
                Log.d(TAG, "Collected State Change ${it.first}, ${it.second}")
                state = state.copy(watchlist = it.first.copy(sort = it.second.movieSort), settings = it.second)
            }

        }
    }

    fun onTabChange(status: WatchStatus) {
        state = state.copy(currentTab = status)
    }

    // Edit Dialog State
    fun showEditDialogFor(movie: Movie) {
        state = state.copy(movieUnderEdit = movie)
    }

    fun hideEditDialog() {
        state = state.copy(movieUnderEdit = null)
    }

    fun onMovieDelete(snackbarState: SnackbarHostState) {
        val movieToDelete = state.movieUnderEdit
        if (movieToDelete == null) return

        viewModelScope.launch {
            movieRepository.removeMovie(movieToDelete)
                .onSuccess {
                    state = state.copy(movieUnderEdit = null)

                    displaySnackbar(
                        message = "Movie Deleted",
                        actionLabel = "UNDO",
                        snackbarState = snackbarState
                    ) {
                        val undoResult = movieRepository.addMovie(movieToDelete)
                            .onFailure {
                                Log.e(TAG, "Couldn't Undo Movie Delete: $it")
                                state = state.copy(error = it)
                            }
                    }

                }.onFailure {
                    Log.e(TAG, "Couldn't Delete Movie: $it")
                    state = state.copy(
                        error = it,
                        movieUnderEdit = null
                    )
                }
        }
    }

    fun onMovieUpdate(edits: EditableState, snackbarState: SnackbarHostState) {
        val movieToUpdate = state.movieUnderEdit
        if (movieToUpdate == null) return

        viewModelScope.launch {
            val updateResult = movieRepository.updateMovie(
                old = movieToUpdate,
                new = movieToUpdate with edits
            )

            updateResult.onSuccess {
                state = state.copy(movieUnderEdit = null)

                displaySnackbar(
                    message = "Updated Movie Successfully",
                    actionLabel = "UNDO",
                    snackbarState = snackbarState,
                ) {
                    val undoResult = movieRepository.updateMovie(
                        old = movieToUpdate with edits,
                        new = movieToUpdate
                    )
                    undoResult.onFailure {
                        Log.e(TAG, "Couldn't Undo Movie Update: $it")
                        state = state.copy(error = it)
                    }
                }

            }.onFailure {
                Log.e(TAG, "Couldn't Update Movie: $it")
                state = state.copy(
                    error = it,
                    movieUnderEdit = null
                )
            }
        }
    }

    // Sort Dialog State
    fun showSortDialog() {
        state = state.copy(showSortDialog = true)
    }

    fun onSortChange(new: Sort) {
        viewModelScope.launch {
            settingsRepository.updateMovieSort(new)
                .onSuccess {
                    state = state.copy(
                        showSortDialog = false,
                        watchlist = state.watchlist.copy(sort = new),
                    )
                }.onFailure {
                    Log.d(TAG, "Failed to update sort method $it")
                    state = state.copy(
                        showSortDialog = false,
                        error = it,
                    )
                }
        }
    }
}