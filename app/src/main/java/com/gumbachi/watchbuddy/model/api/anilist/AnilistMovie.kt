package com.gumbachi.watchbuddy.model.api.anilist

import com.gumbachi.watchbuddy.model.WatchbuddyID
import com.gumbachi.watchbuddy.model.enums.data.API
import com.gumbachi.watchbuddy.model.enums.data.MediaType
import com.gumbachi.watchbuddy.model.enums.data.ReleaseStatus
import com.gumbachi.watchbuddy.model.enums.data.WatchStatus
import com.gumbachi.watchbuddy.model.interfaces.Movie
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate

data class AnilistMovie(
    override val posterURL: String,
    override val title: String,
    override val id: Int,
    override val runtime: String,
    override val releaseDate: LocalDate?,
    override val releaseStatus: ReleaseStatus,

    override var userScore: Int = 0,
    override var userNotes: String = "",
    override var watchStatus: WatchStatus = WatchStatus.Watching,
    override var startDate: LocalDate? = null,
    override var finishDate: LocalDate? = null,
    override var lastUpdate: Instant? = null,

) : Movie {

    override val watchbuddyID = WatchbuddyID(API.Anilist, MediaType.Movie, id)

    // Card Details
    override val primaryDetail = runtime
    override val secondaryDetail = releaseDate.toString()
    override val score: Int
        get() = userScore

    override fun clone(): AnilistMovie = copy()
}
