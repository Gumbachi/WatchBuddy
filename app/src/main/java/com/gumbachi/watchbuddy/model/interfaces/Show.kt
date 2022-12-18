package com.gumbachi.watchbuddy.model.interfaces

import com.gumbachi.watchbuddy.data.local.realm.objects.RealmShow
import java.time.ZoneOffset

interface Show: Media {
    val totalEpisodes: Int
    val episodesWatched: Int

    override fun clone(): Show

    fun toRealmShow(): RealmShow {
        val show = this
        return RealmShow().apply {
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