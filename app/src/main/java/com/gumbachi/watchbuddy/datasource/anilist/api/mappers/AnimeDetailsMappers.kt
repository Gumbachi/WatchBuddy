package com.gumbachi.watchbuddy.data.remote.anilist.mappers

import com.gumbachi.watchbuddy.AnimeDetailsQuery
import com.gumbachi.watchbuddy.datasource.anilist.model.AnilistMovieDetails
import com.gumbachi.watchbuddy.datasource.anilist.model.AnilistShowDetails

fun AnimeDetailsQuery.Data.toAnilistShowDetails(): AnilistShowDetails {
    Media!!.let {
        return AnilistShowDetails(
            id = it.id,
            title = it.title?.english ?: it.title?.romaji ?: it.title?.native!!,
            posterURL = it.coverImage?.extraLarge ?: "",
            backdropURL = it.bannerImage ?: "",
            overview = it.description
        )
    }
}

fun AnimeDetailsQuery.Data.toAnilistMovieDetails(): AnilistMovieDetails {
    Media!!.let {
        return AnilistMovieDetails(
            id = it.id,
            title = it.title?.english ?: it.title?.romaji ?: it.title?.native!!,
            posterURL = it.coverImage?.extraLarge ?: "",
            backdropURL = it.bannerImage ?: "",
            overview = it.description
        )
    }
}