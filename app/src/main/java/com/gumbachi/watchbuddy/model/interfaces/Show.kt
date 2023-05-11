package com.gumbachi.watchbuddy.model.interfaces

import com.gumbachi.watchbuddy.database.objects.RealmShow
import com.gumbachi.watchbuddy.model.enums.data.ReleaseStatus
import com.gumbachi.watchbuddy.utils.toShowReleaseStatus
import kotlinx.datetime.LocalDate

interface Show: Media {
    override val totalEpisodes: Int?
    override var episodesWatched: Int

    val endDate: LocalDate?

    override val releaseStatus: ReleaseStatus
        get() = startDate.toShowReleaseStatus(endDate = endDate)

    override fun clone(): Show

    fun toRealmShow(): RealmShow {
        val show = this
        return RealmShow().apply {
            id = show.watchbuddyID.toString()
            title = show.title
            posterURL = show.posterURL
            releaseDate = show.startDate?.toEpochDays()
            watchStatus = show.watchStatus.toString()
            userScore = show.userScore
            userNotes = show.userNotes

            episodesWatched = show.episodesWatched
            totalEpisodes = show.totalEpisodes

            startDate = show.userStartDate?.toEpochDays()
            finishDate = show.userFinishDate?.toEpochDays()
            lastUpdate = show.lastUpdate?.epochSeconds

            releaseStatus = show.releaseStatus.toString()
        }
    }

}