package com.gumbachi.watchbuddy.model.interfaces

import com.gumbachi.watchbuddy.model.WatchbuddyID

interface Media: Editable, Sortable, Cardable, RealmSavable {
    override val watchbuddyID: WatchbuddyID

    fun clone(): Media
}