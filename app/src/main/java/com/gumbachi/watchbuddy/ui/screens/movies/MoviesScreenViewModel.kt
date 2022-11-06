package com.gumbachi.watchbuddy.ui.screens.movies

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gumbachi.watchbuddy.data.local.MovieRepository
import com.gumbachi.watchbuddy.data.local.SettingsRepository
import com.gumbachi.watchbuddy.model.enums.CardStyle
import com.gumbachi.watchbuddy.model.enums.ScoreFormat
import com.gumbachi.watchbuddy.model.enums.WatchStatus
import com.gumbachi.watchbuddy.model.interfaces.Movie
import com.gumbachi.watchbuddy.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

data class MoviesScreenUiState(
    val loading: Boolean = true,
    val selectedTab: Int = 0,
    val movies: List<Movie>? = null,

    val showEditMenu: Boolean = false,
    val editMovie: Movie? = null,

    val cardStyle: CardStyle = CardStyle.Normal,
    val scoreFormat: ScoreFormat = ScoreFormat.Decimal
)

@HiltViewModel
class MoviesScreenViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
    private val settingsRepository: SettingsRepository
): ViewModel() {

    var state by mutableStateOf(MoviesScreenUiState())

    private fun loadMovies() {
        viewModelScope.launch {
            state = state.copy(loading = true)
            when (val result = movieRepository.getAllMovies()) {
                is Resource.Success -> {
                    state = state.copy(
                        loading = false,
                        movies = result.data
                    )
                }
                is Resource.Error -> {
                    println("There was an error with fetching movies")
                }
            }
        }
    }

    private fun loadMoviesByStatus(status: WatchStatus) {
        viewModelScope.launch {
            state = state.copy(loading = true)
            when (val result = movieRepository.getMoviesByWatchStatus(status)) {
                is Resource.Success -> {
                    state = state.copy(
                        loading = false,
                        movies = result.data
                    )
                }
                is Resource.Error -> {
                    Log.e("Movies","There was an error with fetching movies")
                }
            }
        }
    }

    fun onTabChange(index: Int) {
        state = state.copy(selectedTab = index)
        loadMoviesByStatus(WatchStatus.fromIndex(index))
    }

    fun submitEditedMovie() {

    }

    fun displayEditDialog(movie: Movie) {
        state = state.copy(
            showEditMenu = true,
            editMovie = movie
        )
    }

    fun hideEditDialog() {
        state = state.copy(
            showEditMenu = false,
            editMovie = null
        )
    }

    fun updateSettings() {
        viewModelScope.launch {
            val settings = settingsRepository.getUserSettings()
            state = state.copy(
                cardStyle = settings.cardStyle,
                scoreFormat = settings.scoreFormat
            )
        }
    }

    init {
        Log.d("VM", "Movies VM Created")
        loadMoviesByStatus(WatchStatus.Watching)
        updateSettings()
    }
}