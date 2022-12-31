package com.gumbachi.watchbuddy.model.interfaces

import com.gumbachi.watchbuddy.data.local.realm.objects.RealmMovie
import com.gumbachi.watchbuddy.model.enums.data.ReleaseStatus
import com.gumbachi.watchbuddy.model.enums.data.WatchStatus
import com.gumbachi.watchbuddy.utils.toMovieReleaseStatus

interface Movie: Media {
    val runtime: String

    override val releaseStatus: ReleaseStatus
        get() = releaseDate.toMovieReleaseStatus()

    override fun clone(): Movie

    override var episodesWatched: Int
        get() = if (watchStatus == WatchStatus.Completed) 1 else 0
        set(_) = Unit

    override val totalEpisodes: Int?
        get() = 1

    override fun toRealmObject(): RealmMovie {
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
            releaseStatus = movie.releaseStatus.toString()
        }
    }

}