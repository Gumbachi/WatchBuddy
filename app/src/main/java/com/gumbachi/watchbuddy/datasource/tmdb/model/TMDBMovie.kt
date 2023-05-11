package com.gumbachi.watchbuddy.datasource.tmdb.model

import com.gumbachi.watchbuddy.model.WatchBuddyID
import com.gumbachi.watchbuddy.model.enums.data.API
import com.gumbachi.watchbuddy.model.enums.data.MediaType
import com.gumbachi.watchbuddy.model.enums.data.WatchStatus
import com.gumbachi.watchbuddy.model.interfaces.Movie
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate

data class TMDBMovie(
    override val id: Int,
    override val posterURL: String,
    override val title: String,
    override val startDate: LocalDate?,
    override val runtime: String,

    override var userScore: Int = 0,
    override var userNotes: String = "",
    override var watchStatus: WatchStatus = WatchStatus.Watching,

    override var userStartDate: LocalDate? = null,
    override var userFinishDate: LocalDate? = null,

    override var lastUpdate: Instant? = null,
) : Movie {

    override val watchbuddyID = WatchBuddyID(API.TMDB, MediaType.Movie, id)

    // Card Details
    override val primaryDetail = runtime
    override val secondaryDetail = startDate.toString()
    override val score: Int
        get() = userScore

    override fun clone() = copy() // pass parameters to create deep copy
}
