package com.gumbachi.watchbuddy.model.api.custom

import com.gumbachi.watchbuddy.model.WatchbuddyID
import com.gumbachi.watchbuddy.model.enums.data.API
import com.gumbachi.watchbuddy.model.enums.data.MediaType
import com.gumbachi.watchbuddy.model.enums.data.ReleaseStatus
import com.gumbachi.watchbuddy.model.enums.data.WatchStatus
import com.gumbachi.watchbuddy.model.interfaces.Show
import java.time.LocalDate
import java.time.LocalDateTime

data class CustomShow(
    override val primaryDetail: String,
    override val secondaryDetail: String,
    override val posterURL: String,
    override val score: Int,
    override val title: String,
    override val id: Int,
    override var userScore: Int,
    override var userNotes: String,
    override var watchStatus: WatchStatus,
    override var startDate: LocalDate?,
    override var finishDate: LocalDate?,
    override var lastUpdate: LocalDateTime?,
    override val totalEpisodes: Int,
    override val episodesWatched: Int,
    override val releaseDate: LocalDate,
    override val releaseStatus: ReleaseStatus

) : Show {
    override fun clone(): Show = copy()
    override val watchbuddyID = WatchbuddyID(API.Custom, MediaType.Show, id)
}
