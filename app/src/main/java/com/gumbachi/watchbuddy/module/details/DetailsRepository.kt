package com.gumbachi.watchbuddy.module.details

import android.util.Log
import com.gumbachi.watchbuddy.data.remote.anilist.AnilistAPI
import com.gumbachi.watchbuddy.data.remote.tmdb.TMDBApi
import com.gumbachi.watchbuddy.model.api.anilist.AnilistMovieDetails
import com.gumbachi.watchbuddy.model.api.anilist.AnilistShowDetails
import com.gumbachi.watchbuddy.model.api.tmdb.TMDBMovieDetails
import com.gumbachi.watchbuddy.model.api.tmdb.TMDBShowDetails

private const val TAG = "DetailsRepository"

interface DetailsRepository {
    suspend fun getTMDBMovieDetails(id: Int): TMDBMovieDetails
    suspend fun getTMDBShowDetails(id: Int): TMDBShowDetails
    suspend fun getAnilistMovieDetails(id: Int): AnilistMovieDetails
    suspend fun getAnilistShowDetails(id: Int): AnilistShowDetails
}

class DetailsRepositoryImpl(
    private val tmdb: TMDBApi,
    private val anilist: AnilistAPI
) : DetailsRepository {

    init {
        Log.d(TAG, "Details Repository Created")
    }

    override suspend fun getTMDBMovieDetails(id: Int) = tmdb.getMovieDetails(id)
    override suspend fun getTMDBShowDetails(id: Int) = tmdb.getShowDetails(id)
    override suspend fun getAnilistMovieDetails(id: Int) = anilist.getAnilistMovieDetails(id)
    override suspend fun getAnilistShowDetails(id: Int) = anilist.getAnilistShowDetails(id)

}