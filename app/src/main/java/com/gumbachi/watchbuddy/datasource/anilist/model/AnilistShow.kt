package com.gumbachi.watchbuddy.datasource.anilist.model

import com.gumbachi.watchbuddy.model.WatchBuddyID
import com.gumbachi.watchbuddy.model.enums.data.API
import com.gumbachi.watchbuddy.model.enums.data.MediaType
import com.gumbachi.watchbuddy.model.enums.data.ReleaseStatus
import com.gumbachi.watchbuddy.model.enums.data.WatchStatus
import com.gumbachi.watchbuddy.model.interfaces.Show
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate

data class AnilistShow(
    override val id: Int,
    override val title: String,
    override val posterURL: String,

    override val startDate: LocalDate?,
    override val endDate: LocalDate?,

    override val releaseStatus: ReleaseStatus,

    override var episodesWatched: Int = 0,
    override val totalEpisodes: Int?,

    val nextEpisode: Int? = null,
    val timeUntilNextEpisode: Int? = null,

    override var userScore: Int = 0,
    override var userNotes: String = "",
    override var watchStatus: WatchStatus = WatchStatus.Watching,
    override var userStartDate: LocalDate? = null,
    override var userFinishDate: LocalDate? = null,
    override var lastUpdate: Instant? = null,
): Show {
    override val watchbuddyID = WatchBuddyID(API.Anilist, MediaType.Show, id)

    // Card Details
    override val primaryDetail = when (releaseStatus) {
        ReleaseStatus.Releasing -> "EP $nextEpisode in $timeUntilNextEpisode"
        else -> releaseStatus.toString()
    }
    override val secondaryDetail = "$startDate - $endDate"
    override val progress
        get() = "$episodesWatched / $totalEpisodes"
    override val score: Int
        get() = userScore

    override fun clone() = copy()
}