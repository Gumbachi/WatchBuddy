package com.gumbachi.watchbuddy.model.interfaces

import com.gumbachi.watchbuddy.model.enums.MediaType
import com.gumbachi.watchbuddy.model.enums.ReleaseStatus
import com.gumbachi.watchbuddy.model.enums.SourceAPI
import java.time.LocalDate

interface Media {

    val id: Int
    val type: MediaType
    val api: SourceAPI

    val title: String
    val posterURL: String

    val releaseDate: LocalDate
    val releaseStatus: ReleaseStatus

}