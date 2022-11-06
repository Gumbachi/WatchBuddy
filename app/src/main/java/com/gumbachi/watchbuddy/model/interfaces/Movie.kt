package com.gumbachi.watchbuddy.model.interfaces

import com.gumbachi.watchbuddy.model.enums.MediaType

interface Movie: Editable, Media {
    val runtime: String

    override val type: MediaType
        get() = MediaType.Movie
}