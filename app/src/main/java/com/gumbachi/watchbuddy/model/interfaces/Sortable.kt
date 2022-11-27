package com.gumbachi.watchbuddy.model.interfaces

import com.gumbachi.watchbuddy.model.enums.data.ReleaseStatus
import java.time.LocalDate
import java.time.LocalDateTime

interface Sortable {
    var userScore: Int
    val releaseDate: LocalDate
    val title: String
    val releaseStatus: ReleaseStatus
    var lastUpdate: LocalDateTime?
}