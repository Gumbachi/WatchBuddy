package com.gumbachi.watchbuddy.module.movies

import android.util.Log
import com.gumbachi.watchbuddy.database.WatchbuddyDatabase
import com.gumbachi.watchbuddy.model.Watchlist
import com.gumbachi.watchbuddy.model.enums.configuration.Sort
import com.gumbachi.watchbuddy.model.enums.configuration.SortOrder
import com.gumbachi.watchbuddy.model.interfaces.Movie
import com.gumbachi.watchbuddy.model.settings.UserSettings
import kotlinx.coroutines.flow.*

private const val TAG = "MovieRepository"

interface MoviesRepository {
    suspend fun getSettingsFlow(): Flow<UserSettings>
    suspend fun getMoviesFlow(): Flow<List<Movie>>
    suspend fun getWatchlistFlow(): Flow<Watchlist<Movie>>
    suspend fun addMovie(movie: Movie)
    suspend fun updateMovieTo(updatedMovie: Movie)
    suspend fun removeMovie(movie: Movie)
    suspend fun updateMovieSortTo(sort: Sort, order: SortOrder)

}

class MoviesRepositoryImpl(
    private val db: WatchbuddyDatabase
) : MoviesRepository {

    init {
        Log.d(TAG, "Movie Repository Created")
    }

    override suspend fun getSettingsFlow(): Flow<UserSettings> {
        Log.d(TAG, "Getting Settings Flow")
        return db.getUserSettingsFlow().distinctUntilChanged()
    }

    override suspend fun getWatchlistFlow(): Flow<Watchlist<Movie>> {
        Log.d(TAG, "Fetching Watchlist...")
        val sortFlow = db.getUserSettingsFlow().map { it.movies.sort to it.movies.sortOrder }
        val moviesFlow = db.getMoviesFlow()

        return combine(moviesFlow, sortFlow) { movies, (sort, order) ->
            Watchlist(entries = movies, sort = sort, order = order)
        }.distinctUntilChanged()
    }

    override suspend fun getMoviesFlow(): Flow<List<Movie>> {
        Log.d(TAG, "Fetching Movies...")
        return db.getMoviesFlow()
    }

    override suspend fun addMovie(movie: Movie) {
        Log.d(TAG, "Saving Movie...")
        db.addMovie(movie)
    }

    override suspend fun updateMovieTo(updatedMovie: Movie) {
        Log.d(TAG, "Updating ${updatedMovie.watchbuddyID} to $updatedMovie")
        db.updateMovie(updatedMovie)
    }

    override suspend fun removeMovie(movie: Movie) {
        Log.d(TAG, "Removing $movie")
        db.removeMovie(movie)
    }

    override suspend fun updateMovieSortTo(sort: Sort, order: SortOrder) {
        Log.d(TAG, "Updating movie sort")
        val current = db.getUserSettingsFlow().first()
        val movieSettings = current.movies
        db.updateUserSettingsTo(current.copy(movies = movieSettings.copy(sort = sort, sortOrder = order)))
    }
}