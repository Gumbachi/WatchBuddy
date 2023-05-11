package com.gumbachi.watchbuddy.datasource.anilist.mappers

import com.gumbachi.watchbuddy.AnimeEssentialsQuery
import com.gumbachi.watchbuddy.datasource.anilist.model.AnilistMovie
import com.gumbachi.watchbuddy.datasource.anilist.model.AnilistShow
import com.gumbachi.watchbuddy.model.enums.data.MediaType

fun AnimeEssentialsQuery.Data.toAnilistMovie(): AnilistMovie {

    val mediaType = MediaType.Movie
    val releaseStatus = this.Media?.status.toReleaseStatus(mediaType)

    this.Media!!.let {
        return AnilistMovie(
            posterURL = it.coverImage?.large ?: "",
            title = it.title?.english ?: it.title?.romaji ?: it.title?.native!!,
            id = it.id,
            runtime = it.duration?.toString() ?: "??:??",
            startDate = it.startDate?.toDateOrNull(),
            releaseStatus = releaseStatus
        )
    }
}

fun AnimeEssentialsQuery.Data.toAnilistShow(): AnilistShow {
    val mediaType = MediaType.Show
    val releaseStatus = this.Media?.status.toReleaseStatus(mediaType)
    val media = Media!!
    return AnilistShow(
        posterURL = media.coverImage?.large ?: "",
        title = media.title?.english ?: media.title?.romaji ?: media.title?.native!!,
        id = media.id,
        startDate = media.startDate?.toDateOrNull(),
        releaseStatus = releaseStatus,
        endDate = media.endDate?.toDateOrNull(),
        totalEpisodes = media.episodes,
        timeUntilNextEpisode = media.nextAiringEpisode?.timeUntilAiring,
        nextEpisode = media.nextAiringEpisode?.episode
    )
}

