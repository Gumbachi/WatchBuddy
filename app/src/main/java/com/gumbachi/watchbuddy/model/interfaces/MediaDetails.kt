package com.gumbachi.watchbuddy.model.interfaces

import com.gumbachi.watchbuddy.model.WatchBuddyID

interface MediaDetails {
    val id: Int
    val watchBuddyID: WatchBuddyID
    val title: String
    val posterURL: String
}