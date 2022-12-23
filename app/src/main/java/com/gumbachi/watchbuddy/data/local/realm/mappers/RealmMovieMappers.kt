package com.gumbachi.watchbuddy.data.local.realm.mappers

import com.gumbachi.watchbuddy.data.local.realm.objects.RealmMovie
import com.gumbachi.watchbuddy.model.api.anilist.AnilistMovie
import com.gumbachi.watchbuddy.model.api.custom.CustomMovie
import com.gumbachi.watchbuddy.model.api.tmdb.TMDBMovie
import com.gumbachi.watchbuddy.model.enums.data.API
import com.gumbachi.watchbuddy.model.enums.data.ReleaseStatus
import com.gumbachi.watchbuddy.model.enums.data.WatchStatus
import com.gumbachi.watchbuddy.model.interfaces.Movie
import com.gumbachi.watchbuddy.model.toWatchbuddyID
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate

//region RealmMovie -> Other
fun RealmMovie.toMovie(): Movie {
    val watchbuddyID = id.toWatchbuddyID()
    return when (watchbuddyID.api) {
        API.TMDB -> toTMDBMovie()
        API.Anilist -> toAnilistMovie()
        API.Custom -> toCustomMovie()
    }
}

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
//endregion

//region Other -> RealmMovie
fun Movie.toRealmMovie(): RealmMovie {
    val movie = this
    return RealmMovie().apply {
        id = movie.watchbuddyID.toString()
        title = movie.title
        posterURL = movie.posterURL
        releaseDate = movie.releaseDate?.toEpochDays()
        runtime = movie.runtime
        watchStatus = movie.watchStatus.toString()
        userScore = movie.userScore
        userNotes = movie.userNotes
        startDate = movie.startDate?.toEpochDays()
        finishDate = movie.finishDate?.toEpochDays()
        lastUpdate = movie.lastUpdate?.epochSeconds
        releaseStatus = movie.releaseStatus.toString()
    }
}
//endregion
