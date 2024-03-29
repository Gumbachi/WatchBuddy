package com.gumbachi.watchbuddy.datasource.anilist.mappers

import android.util.Log
import com.gumbachi.watchbuddy.AnimeSearchQuery
import com.gumbachi.watchbuddy.datasource.anilist.model.AnilistSearchResult

private const val TAG = "AnilistSearchQueryMapping"

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
        posterURL = coverImage?.extraLarge ?: "",
        averageScore = averageScore ?: 0,
        popularity = popularity ?: 0,
        type = mediaType,
        releaseStatus = releaseStatus,
        timeUntilNextEpisode = nextAiringEpisode?.timeUntilAiring,
        nextEpisode = nextAiringEpisode?.episode,
        releaseDate = createLocalDateOrNull(startDate?.year, startDate?.month, startDate?.day),
        totalEpisodes = episodes
    )
}

fun AnimeSearchQuery.Data.toAnilistSearchResults(): List<AnilistSearchResult> {
    return Page?.media?.mapNotNull {
        it?.toAnilistAnimeSearchResult()
    } ?: emptyList()
}