package com.gumbachi.watchbuddy.model.interfaces

import com.gumbachi.watchbuddy.model.enums.data.WatchStatus
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate

interface Editable {
    val title: String
    val id: Int

    var episodesWatched: Int
    val totalEpisodes: Int?

    var userScore: Int
    var userNotes: String
    var watchStatus: WatchStatus

    var userStartDate: LocalDate?
    var userFinishDate: LocalDate?
    var lastUpdate: Instant?
}