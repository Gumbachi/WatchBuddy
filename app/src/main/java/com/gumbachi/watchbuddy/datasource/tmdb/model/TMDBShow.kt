package com.gumbachi.watchbuddy.datasource.tmdb.model

import com.gumbachi.watchbuddy.model.WatchBuddyID
import com.gumbachi.watchbuddy.model.enums.data.API
import com.gumbachi.watchbuddy.model.enums.data.MediaType
import com.gumbachi.watchbuddy.model.enums.data.WatchStatus
import com.gumbachi.watchbuddy.model.interfaces.Show
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate

data class TMDBShow(
    override val id: Int,
    override val title: String,
    override val posterURL: String,

    override val startDate: LocalDate?,
    override val endDate: LocalDate?,

    override var episodesWatched: Int = 0,
    override val totalEpisodes: Int?,

    override var userScore: Int = 0,
    override var userNotes: String = "",
    override var watchStatus: WatchStatus = WatchStatus.Watching,
    override var userStartDate: LocalDate? = null,
    override var userFinishDate: LocalDate? = null,
    override var lastUpdate: Instant? = null,

    // potential details
    val episodeRuntime: String = "TODO",
    val lastAirDate: LocalDate? = null

): Show {
    override val watchbuddyID = WatchBuddyID(API.TMDB, MediaType.Show, id)

    // Card Details
    override val primaryDetail = "TBD"
    override val secondaryDetail = "Aired $startDate"
    override val progress
        get() = "$episodesWatched / ${totalEpisodes ?: "??"}"
    override val score: Int
        get() = userScore

    override fun clone() = copy()
}