package com.gumbachi.watchbuddy.database.mappers

import com.gumbachi.watchbuddy.database.objects.RealmShow
import com.gumbachi.watchbuddy.datasource.anilist.model.AnilistShow
import com.gumbachi.watchbuddy.datasource.tmdb.model.TMDBShow
import com.gumbachi.watchbuddy.model.api.custom.CustomShow
import com.gumbachi.watchbuddy.model.enums.data.ReleaseStatus
import com.gumbachi.watchbuddy.model.enums.data.WatchStatus
import com.gumbachi.watchbuddy.model.toWatchbuddyID
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate

fun RealmShow.toTMDBShow(): TMDBShow = TMDBShow(
    id = id.toWatchbuddyID().sourceID,
    posterURL = posterURL,
    title = title,
    releaseDate = releaseDate?.let { LocalDate.fromEpochDays(it) },
    endDate = releaseDate?.let { LocalDate.fromEpochDays(it) },
    episodesWatched = episodesWatched,
    userScore = userScore,
    userNotes = userNotes,
    totalEpisodes = totalEpisodes,
    watchStatus = WatchStatus.valueOf(watchStatus),
    startDate = startDate?.let { LocalDate.fromEpochDays(it) },
    finishDate = finishDate?.let { LocalDate.fromEpochDays(it) },
    lastUpdate = lastUpdate?.let { Instant.fromEpochSeconds(it) }
)

fun RealmShow.toAnilistShow(): AnilistShow = AnilistShow(
    id = id.toWatchbuddyID().sourceID,
    posterURL = posterURL,
    title = title,
    totalEpisodes = totalEpisodes,
    releaseDate = releaseDate?.let { LocalDate.fromEpochDays(it) },
    endDate = releaseDate?.let { LocalDate.fromEpochDays(it) },
    userScore = userScore,
    userNotes = userNotes,
    episodesWatched = episodesWatched,
    watchStatus = WatchStatus.valueOf(watchStatus),
    startDate = startDate?.let { LocalDate.fromEpochDays(it) },
    finishDate = finishDate?.let { LocalDate.fromEpochDays(it) },
    lastUpdate = lastUpdate?.let { Instant.fromEpochSeconds(it) },
    releaseStatus = ReleaseStatus.valueOf(releaseStatus)
)

fun RealmShow.toCustomShow(): CustomShow = CustomShow(
    id = id.toWatchbuddyID().sourceID,
    posterURL = posterURL,
    title = title,
    totalEpisodes = totalEpisodes,
    releaseDate = releaseDate?.let { LocalDate.fromEpochDays(it) },
    endDate = releaseDate?.let { LocalDate.fromEpochDays(it) },
    userScore = userScore,
    episodesWatched = episodesWatched,
    userNotes = userNotes,
    watchStatus = WatchStatus.valueOf(watchStatus),
    startDate = startDate?.let { LocalDate.fromEpochDays(it) },
    finishDate = finishDate?.let { LocalDate.fromEpochDays(it) },
    lastUpdate = lastUpdate?.let { Instant.fromEpochSeconds(it) }
)
