package com.gumbachi.watchbuddy.model.interfaces

import com.gumbachi.watchbuddy.model.enums.WatchStatus
import java.time.LocalDate

interface Editable {
    val id: Int

    var userScore: Double
    var userNotes: String
    var watchStatus: WatchStatus

    var startDate: LocalDate?
    var finishDate: LocalDate?
}