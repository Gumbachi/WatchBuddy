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

    override val releaseDate: LocalDate?,
    override val endDate: LocalDate?,

    override val releaseStatus: ReleaseStatus,

    override var episodesWatched: Int = 0,
    override val totalEpisodes: Int?,

    override var userScore: Int = 0,
    override var userNotes: String = "",
    override var watchStatus: WatchStatus = WatchStatus.Watching,
    override var startDate: LocalDate? = null,
    override var finishDate: LocalDate? = null,
    override var lastUpdate: Instant? = null,
): Show {
    override val watchbuddyID = WatchBuddyID(API.Anilist, MediaType.Show, id)

    // Card Details
    override val primaryDetail = "TBD" // TODO
    override val secondaryDetail = "Aired $releaseDate"
    override val progress
        get() = "$episodesWatched / $totalEpisodes"
    override val score: Int
        get() = userScore

    override fun clone() = copy()
}