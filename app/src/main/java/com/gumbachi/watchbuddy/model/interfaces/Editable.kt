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

data class EditableImpl(
    override val title: String,
    override val id: Int,
    override var userScore: Int = 0,
    override var userNotes: String = "",
    override var watchStatus: WatchStatus = WatchStatus.Watching,
    override var startDate: LocalDate? = null,
    override var finishDate: LocalDate? = null,
    override var lastUpdate: LocalDateTime? = LocalDateTime.now()
): Editable