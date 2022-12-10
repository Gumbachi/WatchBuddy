package com.gumbachi.watchbuddy.model.interfaces

import com.gumbachi.watchbuddy.model.enums.data.WatchStatus
import java.time.LocalDate
import java.time.LocalDateTime

data class DetailableImpl(
    override val id: Int = 550,
    override val title: String = "Details Title Goes Here",
    override val posterURL: String = "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/or06FN3Dka5tukK1e9sl16pB3iy.jpg",
    override val backdropURL: String = "",
    override val overview: String? = "This is a movie/show about something yada yada"

) : Detailable {
    override fun shortDetails() = List(15) { "Howdy: Howdy Howdy / Howdy" }
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