package com.gumbachi.watchbuddy.module.details

import android.util.Log
import com.gumbachi.watchbuddy.data.remote.tmdb.TMDBApi
import com.gumbachi.watchbuddy.data.remote.tmdb.mappers.toTMDBMovieDetails
import com.gumbachi.watchbuddy.data.remote.tmdb.mappers.toTMDBShowDetails
import com.gumbachi.watchbuddy.model.api.tmdb.TMDBMovieDetails
import com.gumbachi.watchbuddy.model.api.tmdb.TMDBShowDetails

interface DetailsRepository {
    suspend fun getTMDBMovieDetails(id: Int): Result<TMDBMovieDetails>
    suspend fun getTMDBShowDetails(id: Int): Result<TMDBShowDetails>
}

class DetailsRepositoryImpl(
    private val tmdb: TMDBApi
) : DetailsRepository {

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
            tmdb.getShowDetails(id).toTMDBShowDetails()
        }
    }

}