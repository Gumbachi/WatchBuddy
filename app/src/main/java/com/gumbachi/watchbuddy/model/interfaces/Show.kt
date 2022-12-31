package com.gumbachi.watchbuddy.model.interfaces

import com.gumbachi.watchbuddy.data.local.realm.objects.RealmShow
import com.gumbachi.watchbuddy.model.enums.data.ReleaseStatus
import com.gumbachi.watchbuddy.utils.toShowReleaseStatus
import kotlinx.datetime.LocalDate

interface Show: Media {
    override val totalEpisodes: Int?
    override var episodesWatched: Int

    val endDate: LocalDate?

    override val releaseStatus: ReleaseStatus
        get() = releaseDate.toShowReleaseStatus(endDate = endDate)

    override fun clone(): Show

    override fun toRealmObject(): RealmShow {
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

            releaseStatus = show.releaseStatus.toString()
        }
    }

}