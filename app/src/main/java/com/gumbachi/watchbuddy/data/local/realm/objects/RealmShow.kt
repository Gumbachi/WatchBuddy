package com.gumbachi.watchbuddy.data.local.realm.objects

import com.gumbachi.watchbuddy.model.api.anilist.AnilistShow
import com.gumbachi.watchbuddy.model.enums.data.API
import com.gumbachi.watchbuddy.model.enums.data.WatchStatus
import com.gumbachi.watchbuddy.model.interfaces.Show
import com.gumbachi.watchbuddy.model.api.tmdb.TMDBShow
import com.gumbachi.watchbuddy.model.toWatchbuddyID
import com.gumbachi.watchbuddy.utils.getMovieReleaseStatus
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneOffset

class RealmShow() : RealmObject {

    @PrimaryKey
    var id = ""

    var title = ""
    var posterURL = ""
    var releaseDate = 0L

    var watchStatus = ""
    var userScore = 0
    var userNotes = ""
    var episodesWatched = 0
    var totalEpisodes = 0


    var startDate: Long? = null
    var finishDate: Long? = null
    var lastUpdate: Long? = null

    //region Converters
    fun toTMDBShow(): TMDBShow = TMDBShow(
        id = id.toWatchbuddyID().sourceID,
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

    fun toAnilistShow(): AnilistShow = TODO("ANILIST SUPPORT")

    fun toCustomShow(): Show = TODO("Custom Show Support")

    // Converters
    fun toShow(): Show = when (id.toWatchbuddyID().api) {
        API.TMDB -> toTMDBShow()
        API.Anilist -> toAnilistShow()
        API.Custom -> toCustomShow()
    }
    //endregion

}