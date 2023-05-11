package com.gumbachi.watchbuddy.model.api.custom

import com.gumbachi.watchbuddy.model.WatchBuddyID
import com.gumbachi.watchbuddy.model.enums.data.API
import com.gumbachi.watchbuddy.model.enums.data.MediaType
import com.gumbachi.watchbuddy.model.enums.data.WatchStatus
import com.gumbachi.watchbuddy.model.interfaces.Show
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate

data class CustomShow(
    override val posterURL: String,
    override val title: String,
    override val id: Int,
    override var userScore: Int,
    override var userNotes: String,
    override var watchStatus: WatchStatus,
    override var userStartDate: LocalDate?,
    override var userFinishDate: LocalDate?,
    override var lastUpdate: Instant?,
    override val totalEpisodes: Int?,
    override var episodesWatched: Int,
    override val startDate: LocalDate?,
    override val endDate: LocalDate?,
) : Show {
    override fun clone(): Show = copy()
    override val watchbuddyID = WatchBuddyID(API.Custom, MediaType.Show, id)

    // Card Details
    override val primaryDetail = "IDK Yet" // TODO: Find something to put here
    override val secondaryDetail = startDate.toString()
    override val score: Int
        get() = userScore

}
