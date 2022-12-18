package com.gumbachi.watchbuddy.model.api.anilist

import com.gumbachi.watchbuddy.model.WatchbuddyID
import com.gumbachi.watchbuddy.model.enums.data.API
import com.gumbachi.watchbuddy.model.enums.data.MediaType
import com.gumbachi.watchbuddy.model.enums.data.ReleaseStatus
import com.gumbachi.watchbuddy.model.enums.data.WatchStatus
import com.gumbachi.watchbuddy.model.interfaces.Show
import java.time.LocalDate
import java.time.LocalDateTime

data class AnilistShow(
    override val id: Int,
    override val title: String,
    override val posterURL: String,

    override val releaseDate: LocalDate,
    override val releaseStatus: ReleaseStatus,

    override var episodesWatched: Int = 0,
    override val totalEpisodes: Int,

    override var userScore: Int = 0,
    override var userNotes: String = "",
    override var watchStatus: WatchStatus = WatchStatus.Watching,
    override var startDate: LocalDate? = null,
    override var finishDate: LocalDate? = null,
    override var lastUpdate: LocalDateTime? = null,
): Show {
    override val watchbuddyID = WatchbuddyID(API.Anilist, MediaType.Show, id)

    // Card Details
    override val primaryDetail = "TBD"
    override val secondaryDetail = "Aired $releaseDate"
    override val progress
        get() = "$episodesWatched / $totalEpisodes"
    override val score: Int
        get() = userScore

    override fun clone() = copy()
}