package com.gumbachi.watchbuddy.model.interfaces

import com.gumbachi.watchbuddy.model.WatchbuddyID
import com.gumbachi.watchbuddy.model.enums.data.ReleaseStatus

interface SearchResult : Cardable {

    val id: Int
    override val watchbuddyID: WatchbuddyID

    override val title: String
    override val posterURL: String
    val averageScore: Int

    override val primaryDetail: String
    override val secondaryDetail: String
    override val releaseStatus: ReleaseStatus

    /** A function to determine the weight of a result in the search results list */
    fun weight(): Double


}