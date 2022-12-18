package com.gumbachi.watchbuddy.model.interfaces

import com.gumbachi.watchbuddy.data.local.realm.objects.RealmShow

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
            releaseDate = show.releaseDate?.toEpochDays()
            watchStatus = show.watchStatus.toString()
            userScore = show.userScore
            userNotes = show.userNotes

            episodesWatched = show.episodesWatched
            totalEpisodes = show.totalEpisodes

            startDate = show.startDate?.toEpochDays()
            finishDate = show.finishDate?.toEpochDays()
            lastUpdate = show.lastUpdate?.epochSeconds
        }
    }
}