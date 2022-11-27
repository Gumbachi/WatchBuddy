package com.gumbachi.watchbuddy.model

import com.gumbachi.watchbuddy.model.enums.data.WatchStatus
import com.gumbachi.watchbuddy.model.interfaces.Editable
import com.gumbachi.watchbuddy.model.interfaces.SearchResult
import java.time.LocalDate
import java.time.LocalDateTime

data class EditableState(
    var userScore: Int = 0,
    var userNotes: String = "",
    var watchStatus: WatchStatus = WatchStatus.Watching,
    var startDate: LocalDate? = null,
    var finishDate: LocalDate? = null,
    var lastUpdate: LocalDateTime? = null
) {

    companion object {
        infix fun from(e: Editable): EditableState = EditableState(
            userScore = e.userScore,
            userNotes = e.userNotes,
            watchStatus = e.watchStatus,
            startDate = e.startDate,
            finishDate = e.finishDate,
            lastUpdate = e.lastUpdate
        )

        infix fun from(s: SearchResult): EditableState = EditableState(
            startDate = LocalDate.now(),
        )
    }
}