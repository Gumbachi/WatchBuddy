package com.gumbachi.watchbuddy.data.local.realm.objects

import com.gumbachi.watchbuddy.model.api.anilist.AnilistMovie
import com.gumbachi.watchbuddy.model.enums.data.API
import com.gumbachi.watchbuddy.model.enums.data.WatchStatus
import com.gumbachi.watchbuddy.model.interfaces.Movie
import com.gumbachi.watchbuddy.model.api.tmdb.TMDBMovie
import com.gumbachi.watchbuddy.model.toWatchbuddyID
import com.gumbachi.watchbuddy.utils.getMovieReleaseStatus
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneOffset

class RealmMovie() : RealmObject {

    @PrimaryKey
    var id = ""

    var title = ""
    var posterURL = ""
    var releaseDate = 0L
    var runtime = ""

    var watchStatus = ""
    var userScore = 0
    var userNotes = ""

    var startDate: Long? = null
    var finishDate: Long? = null
    var lastUpdate: Long? = null

    //region Converters
    fun toTMDBMovie(): TMDBMovie = TMDBMovie(
            id = id.toWatchbuddyID().sourceID,
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

    fun toAnilistMovie(): AnilistMovie = TODO("ANILIST SUPPORT")

    fun toCustomMovie(): Movie = TODO("CUSTOM SUPPORT")

    fun toMovie(): Movie = when (id.toWatchbuddyID().api) {
        API.TMDB -> toTMDBMovie()
        API.Anilist -> toAnilistMovie()
        API.Custom -> toCustomMovie()
    }
    //endregion
}