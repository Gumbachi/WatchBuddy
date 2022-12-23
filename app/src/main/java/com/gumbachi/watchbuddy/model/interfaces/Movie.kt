package com.gumbachi.watchbuddy.model.interfaces

import com.gumbachi.watchbuddy.model.enums.data.ReleaseStatus
import com.gumbachi.watchbuddy.utils.toMovieReleaseStatus

interface Movie: Media {
    val runtime: String

    override val releaseStatus: ReleaseStatus
        get() = releaseDate.toMovieReleaseStatus()

    override fun clone(): Movie

}