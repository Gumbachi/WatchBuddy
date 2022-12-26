package com.gumbachi.watchbuddy.data.local.realm.mappers

import com.gumbachi.watchbuddy.data.local.realm.objects.RealmMovie
import com.gumbachi.watchbuddy.model.api.anilist.AnilistMovie
import com.gumbachi.watchbuddy.model.api.custom.CustomMovie
import com.gumbachi.watchbuddy.model.api.tmdb.TMDBMovie
import com.gumbachi.watchbuddy.model.enums.data.ReleaseStatus
import com.gumbachi.watchbuddy.model.enums.data.WatchStatus
import com.gumbachi.watchbuddy.model.toWatchbuddyID
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate

fun RealmMovie.toTMDBMovie(): TMDBMovie = TMDBMovie(
    id = id.toWatchbuddyID().sourceID,
    posterURL = posterURL,
    title = title,
    releaseDate = releaseDate?.let { LocalDate.fromEpochDays(it) },
    runtime = runtime,
    userScore = userScore,
    userNotes = userNotes,
    watchStatus = WatchStatus.valueOf(watchStatus),
    startDate = startDate?.let { LocalDate.fromEpochDays(it) },
    finishDate = finishDate?.let { LocalDate.fromEpochDays(it) },
    lastUpdate = lastUpdate?.let { Instant.fromEpochSeconds(it) }
)

fun RealmMovie.toAnilistMovie(): AnilistMovie = AnilistMovie(
    id = id.toWatchbuddyID().sourceID,
    posterURL = posterURL,
    title = title,
    releaseDate = releaseDate?.let { LocalDate.fromEpochDays(it) },
    runtime = runtime,
    userScore = userScore,
    userNotes = userNotes,
    watchStatus = WatchStatus.valueOf(watchStatus),
    startDate = startDate?.let { LocalDate.fromEpochDays(it) },
    finishDate = finishDate?.let { LocalDate.fromEpochDays(it) },
    lastUpdate = lastUpdate?.let { Instant.fromEpochSeconds(it) },
    releaseStatus = ReleaseStatus.valueOf(releaseStatus)
)

fun RealmMovie.toCustomMovie(): CustomMovie = CustomMovie(
    id = id.toWatchbuddyID().sourceID,
    posterURL = posterURL,
    title = title,
    releaseDate = releaseDate?.let { LocalDate.fromEpochDays(it) },
    runtime = runtime,
    userScore = userScore,
    userNotes = userNotes,
    watchStatus = WatchStatus.valueOf(watchStatus),
    startDate = startDate?.let { LocalDate.fromEpochDays(it) },
    finishDate = finishDate?.let { LocalDate.fromEpochDays(it) },
    lastUpdate = lastUpdate?.let { Instant.fromEpochSeconds(it) }
)
