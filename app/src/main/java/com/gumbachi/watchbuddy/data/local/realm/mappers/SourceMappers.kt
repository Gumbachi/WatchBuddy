package com.gumbachi.watchbuddy.data.local.realm.mappers

import com.gumbachi.watchbuddy.data.local.realm.objects.RealmMovie
import com.gumbachi.watchbuddy.data.local.realm.objects.RealmShow
import com.gumbachi.watchbuddy.model.enums.data.WatchStatus
import com.gumbachi.watchbuddy.model.tmdb.TMDBMovie
import com.gumbachi.watchbuddy.model.tmdb.TMDBShow
import com.gumbachi.watchbuddy.utils.getMovieReleaseStatus
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneOffset

fun RealmMovie.toTMDBMovie(id: Int) = TMDBMovie(
    id = id,
    posterURL = posterURL,
    title = title,
    releaseDate = LocalDate.ofEpochDay(releaseDate),
    runtime = runtime,
    releaseStatus = LocalDate.ofEpochDay(releaseDate).getMovieReleaseStatus(),
    userScore = userScore,
    userNotes = userNotes,
    watchStatus = WatchStatus.valueOf(watchStatus),
    startDate = startDate?.let { LocalDate.ofEpochDay(it) },
    finishDate = finishDate?.let { LocalDate.ofEpochDay(it) },
    lastUpdate = lastUpdate?.let { LocalDateTime.ofEpochSecond(it, 0, ZoneOffset.UTC) }
)

fun RealmShow.toTMDBShow(id: Int) = TMDBShow(
    id = id,
    title = title,
    posterURL = posterURL,
    releaseDate = LocalDate.ofEpochDay(releaseDate),
    releaseStatus = LocalDate.ofEpochDay(releaseDate).getMovieReleaseStatus(),
    episodesWatched = episodesWatched,
    totalEpisodes = totalEpisodes,
    userScore = userScore,
    userNotes = userNotes,
    watchStatus = WatchStatus.valueOf(watchStatus),
    startDate = startDate?.let { LocalDate.ofEpochDay(it) },
    finishDate = finishDate?.let { LocalDate.ofEpochDay(it) },
    lastUpdate = lastUpdate?.let { LocalDateTime.ofEpochSecond(it, 0, ZoneOffset.UTC) }
)