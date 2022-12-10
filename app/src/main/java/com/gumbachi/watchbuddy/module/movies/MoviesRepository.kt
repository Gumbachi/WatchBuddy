package com.gumbachi.watchbuddy.module.movies

import android.util.Log
import com.gumbachi.watchbuddy.data.local.realm.WatchbuddyDatabase
import com.gumbachi.watchbuddy.model.UserSettings
import com.gumbachi.watchbuddy.model.Watchlist
import com.gumbachi.watchbuddy.model.enums.configuration.Sort
import com.gumbachi.watchbuddy.model.interfaces.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private const val TAG = "MovieRepository"

interface MoviesRepository {
    suspend fun getSettingsFlow(): Flow<UserSettings>
    suspend fun getWatchlistFlow(): Flow<Watchlist<Movie>>
    suspend fun addMovie(movie: Movie)
    suspend fun updateMovieTo(updatedMovie: Movie)
    suspend fun removeMovie(movie: Movie)
    suspend fun updateMovieSortTo(sort: Sort)
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
        val currentSort = db.getUserSettingsFlow().first().movieSort
        return db.getMoviesFlow().distinctUntilChanged().map {
            Watchlist(entries = it, sort = currentSort)
        }
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

    override suspend fun updateMovieSortTo(sort: Sort) {
        Log.d(TAG, "Updating movie sort")
        val current = db.getUserSettingsFlow().first()
        db.updateUserSettingsTo(current.copy(movieSort = sort))
    }
}