package com.gumbachi.watchbuddy.model.interfaces

import com.gumbachi.watchbuddy.data.local.realm.objects.RealmMovie
import java.time.ZoneOffset

interface Movie: Media {
    val runtime: String

    override fun clone(): Movie

    fun toRealmMovie(): RealmMovie {
        val movie = this
        return RealmMovie().apply {
            id = movie.watchbuddyID.toString()
            title = movie.title
            posterURL = movie.posterURL
            releaseDate = movie.releaseDate.toEpochDay()
            runtime = movie.runtime
            watchStatus = movie.watchStatus.toString()
            userScore = movie.userScore
            userNotes = movie.userNotes
            startDate = movie.startDate?.toEpochDay()
            finishDate = movie.finishDate?.toEpochDay()
            lastUpdate = movie.lastUpdate?.toEpochSecond(ZoneOffset.UTC)
        }
    }

}