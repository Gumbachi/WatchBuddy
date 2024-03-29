package com.gumbachi.watchbuddy.model.interfaces

import com.gumbachi.watchbuddy.database.objects.RealmMovie
import com.gumbachi.watchbuddy.model.enums.data.ReleaseStatus
import com.gumbachi.watchbuddy.model.enums.data.WatchStatus
import com.gumbachi.watchbuddy.utils.toMovieReleaseStatus

interface Movie: Media {
    val runtime: String

    override val releaseStatus: ReleaseStatus
        get() = startDate.toMovieReleaseStatus()

    override fun clone(): Movie

    override var episodesWatched: Int
        get() = if (watchStatus == WatchStatus.Completed) 1 else 0
        set(_) = Unit

    override val totalEpisodes: Int?
        get() = 1

    fun toRealmMovie(): RealmMovie {
        val movie = this
        return RealmMovie().apply {
            id = movie.watchbuddyID.toString()
            title = movie.title
            posterURL = movie.posterURL
            releaseDate = movie.startDate?.toEpochDays()
            runtime = movie.runtime
            watchStatus = movie.watchStatus.toString()
            userScore = movie.userScore
            userNotes = movie.userNotes
            startDate = movie.userStartDate?.toEpochDays()
            finishDate = movie.userFinishDate?.toEpochDays()
            lastUpdate = movie.lastUpdate?.epochSeconds
            releaseStatus = movie.releaseStatus.toString()
        }
    }

}