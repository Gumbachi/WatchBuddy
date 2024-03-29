package com.gumbachi.watchbuddy.model.api.custom

import com.gumbachi.watchbuddy.model.WatchBuddyID
import com.gumbachi.watchbuddy.model.enums.data.API
import com.gumbachi.watchbuddy.model.enums.data.MediaType
import com.gumbachi.watchbuddy.model.enums.data.WatchStatus
import com.gumbachi.watchbuddy.model.interfaces.Movie
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate

// TODO: Implement this class better
data class CustomMovie(
    override val id: Int,
    override val posterURL: String,
    override val title: String,
    override var userScore: Int,
    override var userNotes: String,
    override var watchStatus: WatchStatus,
    override var userStartDate: LocalDate?,
    override var userFinishDate: LocalDate?,
    override var lastUpdate: Instant?,
    override val startDate: LocalDate?,
    override val runtime: String
): Movie {

    override val watchbuddyID = WatchBuddyID(API.Custom, MediaType.Movie, id)

    // Card Details
    override val primaryDetail = runtime
    override val secondaryDetail = startDate.toString()
    override val score: Int
        get() = userScore

    override fun clone(): CustomMovie = copy()
}
