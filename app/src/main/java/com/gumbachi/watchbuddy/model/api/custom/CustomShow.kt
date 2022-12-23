package com.gumbachi.watchbuddy.model.api.custom

import com.gumbachi.watchbuddy.model.WatchbuddyID
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
    override var startDate: LocalDate?,
    override var finishDate: LocalDate?,
    override var lastUpdate: Instant?,
    override val totalEpisodes: Int,
    override val episodesWatched: Int,
    override val releaseDate: LocalDate?,
    override val endDate: LocalDate?,
) : Show {
    override fun clone(): Show = copy()
    override val watchbuddyID = WatchbuddyID(API.Custom, MediaType.Show, id)

    // Card Details
    override val primaryDetail = "IDK Yet" // TODO: Find something to put here
    override val secondaryDetail = releaseDate.toString()
    override val score: Int
        get() = userScore

}
