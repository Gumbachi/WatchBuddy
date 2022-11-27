package com.gumbachi.watchbuddy.data.repository

import android.util.Log
import com.gumbachi.watchbuddy.data.remote.tmdb.TMDBApi
import com.gumbachi.watchbuddy.data.remote.tmdb.mappers.toTMDBMovieDetails
import com.gumbachi.watchbuddy.model.tmdb.TMDBMovieDetails
import com.gumbachi.watchbuddy.model.tmdb.TMDBShowDetails
import javax.inject.Inject

interface DetailsRepository {
    suspend fun getTMDBMovieDetails(id: Int): Result<TMDBMovieDetails>
    suspend fun getTMDBShowDetails(id: Int): Result<TMDBShowDetails>
}

class DetailsRepositoryImpl @Inject constructor(
    private val tmdb: TMDBApi
): DetailsRepository {

    init {
        Log.d("Repository", "Details Repository Created")
    }

    override suspend fun getTMDBMovieDetails(id: Int): Result<TMDBMovieDetails> {
        return runCatching {
            tmdb.getMovieDetails(id).toTMDBMovieDetails()
        }
    }

    override suspend fun getTMDBShowDetails(id: Int): Result<TMDBShowDetails> {
        return runCatching {
            TODO()
//            tmdb.getShowDetails(id).toTMDBShowDetails()
        }
    }

}