package com.gumbachi.watchbuddy.data.remote.anilist.mappers

import android.util.Log
import com.gumbachi.watchbuddy.AnimeSearchQuery
import com.gumbachi.watchbuddy.BlankAnimeQuery
import com.gumbachi.watchbuddy.model.api.anilist.AnilistMovie
import com.gumbachi.watchbuddy.model.api.anilist.AnilistSearchResult
import com.gumbachi.watchbuddy.model.api.anilist.AnilistShow
import com.gumbachi.watchbuddy.model.enums.data.MediaType
import com.gumbachi.watchbuddy.model.enums.data.ReleaseStatus
import com.gumbachi.watchbuddy.type.MediaFormat
import com.gumbachi.watchbuddy.type.MediaStatus
import kotlinx.datetime.LocalDate

private const val TAG = "AnilistMappers"

fun MediaStatus?.toReleaseStatus(type: MediaType): ReleaseStatus {
    return when (this) {
        MediaStatus.FINISHED -> {
            if (type == MediaType.Show)
                ReleaseStatus.Finished
            else
                ReleaseStatus.Released
        }

        MediaStatus.RELEASING -> ReleaseStatus.Releasing
        MediaStatus.NOT_YET_RELEASED -> ReleaseStatus.Unreleased
        MediaStatus.CANCELLED -> ReleaseStatus.Cancelled
        else -> ReleaseStatus.Unknown
    }
}

fun MediaFormat?.toMediaType(): MediaType {
    return when (this) {
        MediaFormat.TV,
        MediaFormat.TV_SHORT,
        MediaFormat.ONA,
        MediaFormat.OVA,
        MediaFormat.SPECIAL -> MediaType.Show
        MediaFormat.MOVIE -> MediaType.Movie
        else -> {
            Log.e(TAG, "Couldn't validate Media Type of $this")
            throw Exception("Couldn't parse media type")
        }
    }
}

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



fun AnimeSearchQuery.Medium.toAnilistAnimeSearchResult(): AnilistSearchResult? {

    val mediaType = try {
        format.toMediaType()
    } catch(e: Exception) {
        null
    } ?: return null

    val releaseStatus = status.toReleaseStatus(type = mediaType)

    return AnilistSearchResult(
        id = id,
        title = title?.english ?: title?.romaji ?: title?.native ?: run {
            Log.d(TAG, "No Name Provided for Search Result")
            return null
        },
        posterURL = coverImage?.medium ?: "",
        averageScore = averageScore ?: 0,
        popularity = popularity ?: 0,
        type = mediaType,
        releaseStatus = releaseStatus
    )
}

fun AnimeSearchQuery.Data.toAnilistAnimeSearchResults(): List<AnilistSearchResult> {
    return Page?.media?.mapNotNull {
        it?.toAnilistAnimeSearchResult()
    } ?: emptyList()
}

fun BlankAnimeQuery.Data.toAnilistShow(): AnilistShow {

    val mediaType = MediaType.Show
    val releaseStatus = this.Media?.status.toReleaseStatus(mediaType)

    this.Media!!.let {
        return AnilistShow(
            posterURL = it.coverImage?.medium ?: "",
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
            posterURL = it.coverImage?.medium ?: "",
            title = it.title?.english ?: it.title?.romaji ?: it.title?.native!!,
            id = it.id,
            lastUpdate = null,
            runtime = "", // TODO Need this info
            releaseDate = it.startDate?.toDateOrNull(),
            releaseStatus = releaseStatus
        )
    }
}
