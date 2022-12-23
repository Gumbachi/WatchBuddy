package com.gumbachi.watchbuddy.model.interfaces

import com.gumbachi.watchbuddy.model.enums.data.ReleaseStatus
import com.gumbachi.watchbuddy.utils.toShowReleaseStatus
import kotlinx.datetime.LocalDate

interface Show: Media {
    val totalEpisodes: Int
    val episodesWatched: Int
    val endDate: LocalDate?

    override fun clone(): Show

    override val releaseStatus: ReleaseStatus
        get() = releaseDate.toShowReleaseStatus(endDate = endDate)

}