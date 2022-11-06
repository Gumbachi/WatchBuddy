package com.gumbachi.watchbuddy.data.local

import android.util.Log
import com.gumbachi.watchbuddy.data.remote.tmdb.TMDBApi
import com.gumbachi.watchbuddy.data.remote.tmdb.mappers.toTMDBMovieDetails
import com.gumbachi.watchbuddy.data.remote.tmdb.mappers.toTMDBShowDetails
import com.gumbachi.watchbuddy.model.tmdb.TMDBMovieDetails
import com.gumbachi.watchbuddy.model.tmdb.TMDBShowDetails
import com.gumbachi.watchbuddy.utils.Resource
import kotlinx.coroutines.delay
import javax.inject.Inject

interface DetailsRepository {

    suspend fun getTMDBMovieDetails(id: Int): Resource<TMDBMovieDetails>

    suspend fun getTMDBShowDetails(id: Int): Resource<TMDBShowDetails>

}

class DetailsRepositoryImpl @Inject constructor(
    private val tmdbApi: TMDBApi
): DetailsRepository {

    init {
        Log.d("Repository", "Details Repository Created")
    }

    override suspend fun getTMDBMovieDetails(id: Int): Resource<TMDBMovieDetails> {
        delay(1000L)
        return try {
            val details = tmdbApi.getMovieDetails(id)
            Resource.Success(details.toTMDBMovieDetails())
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "Unknown Error Occurred")
        }
    }

    override suspend fun getTMDBShowDetails(id: Int): Resource<TMDBShowDetails> {
        delay(1000L)
        return try {
            val details = tmdbApi.getShowDetails(id)
            Resource.Success(details.toTMDBShowDetails())
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "Unknown Error Occurred")
        }
    }

}