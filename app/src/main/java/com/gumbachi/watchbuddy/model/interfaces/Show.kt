package com.gumbachi.watchbuddy.model.interfaces

import com.gumbachi.watchbuddy.model.enums.MediaType

interface Show: Media {

    override val type: MediaType
        get() = MediaType.Show
}