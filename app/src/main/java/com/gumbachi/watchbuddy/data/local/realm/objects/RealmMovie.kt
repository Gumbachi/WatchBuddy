package com.gumbachi.watchbuddy.data.local.realm.objects

import com.gumbachi.watchbuddy.model.api.anilist.AnilistMovie
import com.gumbachi.watchbuddy.model.api.custom.CustomMovie
import com.gumbachi.watchbuddy.model.api.tmdb.TMDBMovie
import com.gumbachi.watchbuddy.model.enums.data.API
import com.gumbachi.watchbuddy.model.enums.data.WatchStatus
import com.gumbachi.watchbuddy.model.interfaces.Movie
import com.gumbachi.watchbuddy.model.toWatchbuddyID
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate

class RealmMovie() : RealmObject {

    @PrimaryKey
    var id = ""

    var title = ""
    var posterURL = ""
    var releaseDate: Int? = null
    var runtime = ""

    var watchStatus = ""
    var userScore = 0
    var userNotes = ""

    var startDate: Int? = null
    var finishDate: Int? = null
    var lastUpdate: Long? = null

    //region Converters
    fun toTMDBMovie(): TMDBMovie = TMDBMovie(
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

    fun toAnilistMovie(): AnilistMovie = TODO("ANILIST SUPPORT")

    fun toCustomMovie(): CustomMovie = TODO("CUSTOM SUPPORT")

    fun toMovie(): Movie = when (id.toWatchbuddyID().api) {
        API.TMDB -> toTMDBMovie()
        API.Anilist -> toAnilistMovie()
        API.Custom -> toCustomMovie()
    }
    //endregion
}