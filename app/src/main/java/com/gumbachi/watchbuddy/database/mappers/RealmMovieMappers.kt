package com.gumbachi.watchbuddy.database.mappers

import com.gumbachi.watchbuddy.database.objects.RealmMovie
import com.gumbachi.watchbuddy.datasource.anilist.model.AnilistMovie
import com.gumbachi.watchbuddy.datasource.tmdb.model.TMDBMovie
import com.gumbachi.watchbuddy.model.api.custom.CustomMovie
import com.gumbachi.watchbuddy.model.enums.data.ReleaseStatus
import com.gumbachi.watchbuddy.model.enums.data.WatchStatus
import com.gumbachi.watchbuddy.model.toWatchbuddyID
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate

fun RealmMovie.toTMDBMovie(): TMDBMovie = TMDBMovie(
    id = id.toWatchbuddyID().sourceID,
    posterURL = posterURL,
    title = title,
    startDate = releaseDate?.let { LocalDate.fromEpochDays(it) },
    runtime = runtime,
    userScore = userScore,
    userNotes = userNotes,
    watchStatus = WatchStatus.valueOf(watchStatus),
    userStartDate = startDate?.let { LocalDate.fromEpochDays(it) },
    userFinishDate = finishDate?.let { LocalDate.fromEpochDays(it) },
    lastUpdate = lastUpdate?.let { Instant.fromEpochSeconds(it) }
)

fun RealmMovie.toAnilistMovie(): AnilistMovie = AnilistMovie(
    id = id.toWatchbuddyID().sourceID,
    posterURL = posterURL,
    title = title,
    startDate = releaseDate?.let { LocalDate.fromEpochDays(it) },
    runtime = runtime,
    userScore = userScore,
    userNotes = userNotes,
    watchStatus = WatchStatus.valueOf(watchStatus),
    userStartDate = startDate?.let { LocalDate.fromEpochDays(it) },
    userFinishDate = finishDate?.let { LocalDate.fromEpochDays(it) },
    lastUpdate = lastUpdate?.let { Instant.fromEpochSeconds(it) },
    releaseStatus = ReleaseStatus.valueOf(releaseStatus)
)

fun RealmMovie.toCustomMovie(): CustomMovie = CustomMovie(
    id = id.toWatchbuddyID().sourceID,
    posterURL = posterURL,
    title = title,
    startDate = releaseDate?.let { LocalDate.fromEpochDays(it) },
    runtime = runtime,
    userScore = userScore,
    userNotes = userNotes,
    watchStatus = WatchStatus.valueOf(watchStatus),
    userStartDate = startDate?.let { LocalDate.fromEpochDays(it) },
    userFinishDate = finishDate?.let { LocalDate.fromEpochDays(it) },
    lastUpdate = lastUpdate?.let { Instant.fromEpochSeconds(it) }
)
