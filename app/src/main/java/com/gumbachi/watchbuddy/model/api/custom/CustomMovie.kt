package com.gumbachi.watchbuddy.model.api.custom

import com.gumbachi.watchbuddy.model.WatchbuddyID
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
    override var startDate: LocalDate?,
    override var finishDate: LocalDate?,
    override var lastUpdate: Instant?,
    override val releaseDate: LocalDate?,
    override val runtime: String
): Movie {

    override val watchbuddyID = WatchbuddyID(API.Custom, MediaType.Movie, id)

    // Card Details
    override val primaryDetail = runtime
    override val secondaryDetail = releaseDate.toString()
    override val score: Int
        get() = userScore

    override fun clone(): CustomMovie = copy()
}
