package com.gumbachi.watchbuddy.model.interfaces

import com.gumbachi.watchbuddy.model.enums.data.ReleaseStatus
import com.gumbachi.watchbuddy.model.enums.data.Source

interface SearchResult {

    val id: Int
    val source: Source

    val title: String
    val posterURL: String
    val averageScore: Int

    val primaryDetail: String
    val secondaryDetail: String
    val releaseStatus: ReleaseStatus

    /** A function to determine the weight of a result in the search results list */
    fun weight(): Double

}