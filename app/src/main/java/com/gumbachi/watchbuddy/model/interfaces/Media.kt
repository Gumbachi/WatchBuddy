package com.gumbachi.watchbuddy.model.interfaces

import com.gumbachi.watchbuddy.model.WatchBuddyID
import com.gumbachi.watchbuddy.model.enums.data.MediaType

interface Media: Editable, Sortable, Cardable {
    override val watchbuddyID: WatchBuddyID

    val type: MediaType
        get() = watchbuddyID.type

    val isEpisodic: Boolean
        get() = type.isEpisodic

    fun clone(): Media
}