package com.gumbachi.watchbuddy.model.interfaces

import com.gumbachi.watchbuddy.data.local.realm.objects.RealmMovie
import com.gumbachi.watchbuddy.model.enums.data.ReleaseStatus

interface Movie: Media {
    val runtime: String

    override val releaseStatus: ReleaseStatus

    override fun clone(): Movie

    fun toRealmMovie(): RealmMovie {
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
        }
    }

}