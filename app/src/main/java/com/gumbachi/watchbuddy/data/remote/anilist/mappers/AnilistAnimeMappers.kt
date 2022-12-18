package com.gumbachi.watchbuddy.data.remote.anilist.mappers

import android.util.Log
import com.gumbachi.watchbuddy.AnimeSearchResultsDTOQuery
import com.gumbachi.watchbuddy.model.anilist.AnilistAnimeSearchResult
import com.gumbachi.watchbuddy.model.enums.data.MediaType
import com.gumbachi.watchbuddy.model.enums.data.ReleaseStatus
import com.gumbachi.watchbuddy.type.MediaFormat
import com.gumbachi.watchbuddy.type.MediaStatus

private const val TAG = "AnilistMappers"

fun AnimeSearchResultsDTOQuery.Medium.toAnilistAnimeSearchResult(): AnilistAnimeSearchResult? {

    val mediaFormat = when (format) {
        MediaFormat.TV, MediaFormat.TV_SHORT, MediaFormat.ONA, MediaFormat.OVA, MediaFormat.SPECIAL -> MediaType.Show
        MediaFormat.MOVIE -> MediaType.Movie
        else -> {
            Log.d(TAG, "Couldn't validate Media Type of $format")
            return null
        }
    }

    val releaseStatus = when (status) {
        MediaStatus.FINISHED -> ReleaseStatus.Released()
        MediaStatus.RELEASING -> ReleaseStatus.Releasing()
        MediaStatus.NOT_YET_RELEASED -> ReleaseStatus.Unreleased()
        MediaStatus.CANCELLED -> ReleaseStatus.Released("Cancelled")
        else -> ReleaseStatus.Unknown()
    }

    return AnilistAnimeSearchResult(
        id = id,
        title = title?.english ?: title?.romaji ?: title?.native ?: run {
            Log.d(TAG, "No Name Provided for Search Result")
            return null
        },
        posterURL = coverImage?.medium ?: "",
        averageScore = averageScore ?: 0,
        popularity = popularity ?: 0,
        type = mediaFormat,
        releaseStatus = releaseStatus
    )
}

fun AnimeSearchResultsDTOQuery.Data.toAnilistAnimeSearchResults(): List<AnilistAnimeSearchResult> {
    return Page?.media?.mapNotNull {
        it?.toAnilistAnimeSearchResult()
    } ?: emptyList()
}