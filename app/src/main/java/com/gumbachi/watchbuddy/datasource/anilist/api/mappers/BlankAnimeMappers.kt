package com.gumbachi.watchbuddy.datasource.anilist.api.mappers

import com.gumbachi.watchbuddy.BlankAnimeQuery
import com.gumbachi.watchbuddy.datasource.anilist.model.AnilistMovie
import com.gumbachi.watchbuddy.datasource.anilist.model.AnilistShow
import com.gumbachi.watchbuddy.model.enums.data.MediaType
import kotlinx.datetime.LocalDate

fun BlankAnimeQuery.StartDate.toDateOrNull(): LocalDate? {
    return try {
        LocalDate(year!!, month!!, day!!)
    } catch (e: Exception) {
        null
    }
}

fun BlankAnimeQuery.EndDate.toDateOrNull(): LocalDate? {
    return try {
        LocalDate(year!!, month!!, day!!)
    } catch (e: Exception) {
        null
    }
}

fun BlankAnimeQuery.Data.toAnilistShow(): AnilistShow {

    val mediaType = MediaType.Show
    val releaseStatus = this.Media?.status.toReleaseStatus(mediaType)

    this.Media!!.let {
        return AnilistShow(
            posterURL = it.coverImage?.extraLarge ?: "",
            title = it.title?.english ?: it.title?.romaji ?: it.title?.native!!,
            id = it.id,
            lastUpdate = null,
            releaseDate = it.startDate?.toDateOrNull(),
            releaseStatus = releaseStatus,
            endDate = it.endDate?.toDateOrNull(),
            totalEpisodes = it.episodes ?: -1 // TODO Nullable
        )
    }
}

fun BlankAnimeQuery.Data.toAnilistMovie(): AnilistMovie {

    val mediaType = MediaType.Movie
    val releaseStatus = this.Media?.status.toReleaseStatus(mediaType)

    this.Media!!.let {
        return AnilistMovie(
            posterURL = it.coverImage?.extraLarge ?: "",
            title = it.title?.english ?: it.title?.romaji ?: it.title?.native!!,
            id = it.id,
            lastUpdate = null,
            runtime = "", // TODO Need this info
            releaseDate = it.startDate?.toDateOrNull(),
            releaseStatus = releaseStatus
        )
    }
}