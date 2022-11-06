package com.gumbachi.watchbuddy.data.local

import android.util.Log
import com.gumbachi.watchbuddy.model.enums.WatchStatus
import com.gumbachi.watchbuddy.model.interfaces.Movie
import com.gumbachi.watchbuddy.model.tmdb.TMDBMovie
import com.gumbachi.watchbuddy.utils.Resource
import javax.inject.Inject

interface MovieRepository {

    suspend fun getAllMovies(): Resource<List<Movie>>

    suspend fun getMoviesByWatchStatus(status: WatchStatus): Resource<List<Movie>>

}

class MovieRepositoryImpl @Inject constructor(): MovieRepository {

    private val _movies = List(50) { TMDBMovie.createDummy() }

    init {
        Log.d("Repository", "Movie Repository Created")
    }

    override suspend fun getAllMovies(): Resource<List<Movie>> {
        return try {
            Resource.Success(_movies)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "Unknown Error Occurred")
        }
    }

    override suspend fun getMoviesByWatchStatus(status: WatchStatus): Resource<List<Movie>> {
        return try {
            Resource.Success(_movies.filter { it.watchStatus == status })
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "Unknown Error Occurred")
        }
    }
}