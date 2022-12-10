package com.gumbachi.watchbuddy.data.local.realm.objects

import com.gumbachi.watchbuddy.data.local.realm.mappers.toTMDBShow
import com.gumbachi.watchbuddy.model.WatchbuddyID
import com.gumbachi.watchbuddy.model.enums.data.API
import com.gumbachi.watchbuddy.model.interfaces.Show
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
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

    // Converters
    fun toShow(): Show {
        val watchbuddyID = WatchbuddyID from id
        return when (watchbuddyID.api) {
            API.TMDB -> toTMDBShow(watchbuddyID.sourceID)
            API.Anilist -> TODO()
            API.Unknown -> TODO()
        }
    }

    companion object {
        infix fun from(show: Show): RealmShow = RealmShow().apply {
            id = show.watchbuddyID.toString()
            title = show.title
            posterURL = show.posterURL
            releaseDate = show.releaseDate.toEpochDay()
            watchStatus = show.watchStatus.toString()
            userScore = show.userScore
            userNotes = show.userNotes

            episodesWatched = show.episodesWatched
            totalEpisodes = show.totalEpisodes

            startDate = show.startDate?.toEpochDay()
            finishDate = show.finishDate?.toEpochDay()
            lastUpdate = show.lastUpdate?.toEpochSecond(ZoneOffset.UTC)
        }
    }
}