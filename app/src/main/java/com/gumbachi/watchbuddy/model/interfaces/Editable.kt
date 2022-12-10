package com.gumbachi.watchbuddy.model.interfaces

import com.gumbachi.watchbuddy.model.enums.data.WatchStatus
import java.time.LocalDate
import java.time.LocalDateTime

interface Editable {
    val title: String
    val id: Int

    var userScore: Int
    var userNotes: String
    var watchStatus: WatchStatus

    var startDate: LocalDate?
    var finishDate: LocalDate?

    var lastUpdate: LocalDateTime?
}