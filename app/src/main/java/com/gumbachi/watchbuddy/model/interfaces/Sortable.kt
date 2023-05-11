package com.gumbachi.watchbuddy.model.interfaces

import com.gumbachi.watchbuddy.model.enums.data.ReleaseStatus
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate

interface Sortable {
    var userScore: Int
    val title: String
    val releaseStatus: ReleaseStatus

    val startDate: LocalDate?
    var lastUpdate: Instant?
}