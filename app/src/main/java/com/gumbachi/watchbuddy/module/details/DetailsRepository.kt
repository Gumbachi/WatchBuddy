package com.gumbachi.watchbuddy.module.details

import android.util.Log
import com.gumbachi.watchbuddy.datasource.anilist.AnilistAPI
import com.gumbachi.watchbuddy.datasource.anilist.model.AnilistAnimeDetails
import com.gumbachi.watchbuddy.datasource.tmdb.TMDBApi
import com.gumbachi.watchbuddy.datasource.tmdb.model.TMDBMovieDetails
import com.gumbachi.watchbuddy.datasource.tmdb.model.TMDBShowDetails

private const val TAG = "DetailsRepository"

interface DetailsRepository {
    suspend fun getTMDBMovieDetails(id: Int): TMDBMovieDetails
    suspend fun getTMDBShowDetails(id: Int): TMDBShowDetails
    suspend fun getAnilistAnimeDetails(id: Int): AnilistAnimeDetails
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
    override suspend fun getAnilistAnimeDetails(id: Int) = anilist.getAnimeDetails(id)

}