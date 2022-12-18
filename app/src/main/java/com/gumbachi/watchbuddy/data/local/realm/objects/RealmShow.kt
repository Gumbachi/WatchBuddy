package com.gumbachi.watchbuddy.data.local.realm.objects

import com.gumbachi.watchbuddy.model.api.anilist.AnilistShow
import com.gumbachi.watchbuddy.model.api.custom.CustomShow
import com.gumbachi.watchbuddy.model.api.tmdb.TMDBShow
import com.gumbachi.watchbuddy.model.enums.data.API
import com.gumbachi.watchbuddy.model.enums.data.WatchStatus
import com.gumbachi.watchbuddy.model.interfaces.Show
import com.gumbachi.watchbuddy.model.toWatchbuddyID
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate

class RealmShow() : RealmObject {

    @PrimaryKey
    var id = ""

    var title = ""
    var posterURL = ""
    var releaseDate: Int? = null

    var watchStatus = ""
    var userScore = 0
    var userNotes = ""
    var episodesWatched = 0
    var totalEpisodes = 0


    var startDate: Int? = null
    var finishDate: Int? = null
    var lastUpdate: Long? = null

    //region Converters
    fun toTMDBShow(): TMDBShow = TMDBShow(
        id = id.toWatchbuddyID().sourceID,
        title = title,
        posterURL = posterURL,
        releaseDate = releaseDate?.let { LocalDate.fromEpochDays(it) },
        episodesWatched = episodesWatched,
        totalEpisodes = totalEpisodes,
        userScore = userScore,
        userNotes = userNotes,
        watchStatus = WatchStatus.valueOf(watchStatus),
        startDate = startDate?.let { LocalDate.fromEpochDays(it) },
        finishDate = finishDate?.let { LocalDate.fromEpochDays(it) },
        lastUpdate = lastUpdate?.let { Instant.fromEpochSeconds(it)}
    )

    fun toAnilistShow(): AnilistShow = TODO("ANILIST SUPPORT")

    fun toCustomShow(): CustomShow = TODO("Custom Show Support")

    // Converters
    fun toShow(): Show = when (id.toWatchbuddyID().api) {
        API.TMDB -> toTMDBShow()
        API.Anilist -> toAnilistShow()
        API.Custom -> toCustomShow()
    }
    //endregion

}