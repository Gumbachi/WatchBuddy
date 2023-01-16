package com.gumbachi.watchbuddy.model.interfaces

import com.gumbachi.watchbuddy.model.WatchBuddyID
import com.gumbachi.watchbuddy.model.enums.data.ReleaseStatus

interface Cardable {
    val title: String
    val watchbuddyID: WatchBuddyID
    val primaryDetail: String
    val secondaryDetail: String
    val posterURL: String
    val progress: String?
        get() = null
    val score: Int

    val releaseStatus: ReleaseStatus
}