package com.gumbachi.watchbuddy.data.repository

import android.util.Log
import com.gumbachi.watchbuddy.data.local.realm.WatchbuddyDatabase
import com.gumbachi.watchbuddy.model.Watchlist
import com.gumbachi.watchbuddy.model.enums.configuration.Sort
import com.gumbachi.watchbuddy.model.interfaces.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private const val TAG = "MovieRepository"

interface MovieRepository {

    suspend fun getAllMovies(): Result<List<Movie>>
    suspend fun getWatchList(sort: Sort): Flow<Watchlist<Movie>>
    suspend fun addMovie(movie: Movie): Result<Unit>
    suspend fun updateMovie(old: Movie, new: Movie): Result<Unit>
    suspend fun removeMovie(movie: Movie): Result<Unit>

}

class MovieRepositoryImpl @Inject constructor(
    private val db: WatchbuddyDatabase
): MovieRepository {

    init {
        Log.d(TAG, "Movie Repository Created")
    }

    override suspend fun getWatchList(sort: Sort): Flow<Watchlist<Movie>> {
        Log.d(TAG, "Fetching Watchlist...")
        return db.getMoviesFlow().map {
            Watchlist(entries = it, sort = sort)
        }
    }

    override suspend fun getAllMovies(): Result<List<Movie>> {
        Log.d(TAG, "Getting All Movies...")
        return runCatching {
            db.getMoviesFlow().first()
        }
    }


    override suspend fun addMovie(movie: Movie): Result<Unit> {
        return runCatching {
            Log.d(TAG, "Saving Movie...")
            db.writeMovie(movie)
        }
    }

    override suspend fun updateMovie(old: Movie, new: Movie): Result<Unit> {
        return runCatching {
            Log.d(TAG, "Updating $old to $new")
            db.updateMovie(old, new)
        }
    }

    override suspend fun removeMovie(movie: Movie): Result<Unit> {
        Log.d(TAG, "Removing $movie")
        return runCatching {
            db.eraseMovie(movie)
        }
    }
}