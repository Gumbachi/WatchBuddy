package com.gumbachi.watchbuddy.model.interfaces

import com.gumbachi.watchbuddy.model.enums.MediaType
import com.gumbachi.watchbuddy.model.enums.ReleaseStatus
import com.gumbachi.watchbuddy.model.enums.SourceAPI

interface SearchResult {

    val api: SourceAPI
    val type: MediaType
    val id: Int

    val title: String
    val posterURL: String
    val averageScore: Double

    val primaryDetail: String
    val secondaryDetail: String
    val releaseStatus: ReleaseStatus

    /** A function to determine the weight of a result in the search results list */
    fun weight(): Double


}