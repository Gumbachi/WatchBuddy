package com.gumbachi.watchbuddy.model.api.anilist

import com.gumbachi.watchbuddy.model.WatchbuddyID
import com.gumbachi.watchbuddy.model.enums.data.API
import com.gumbachi.watchbuddy.model.enums.data.MediaType
import com.gumbachi.watchbuddy.model.enums.data.ReleaseStatus
import com.gumbachi.watchbuddy.model.interfaces.SearchResult

data class AnilistSearchResult(
    override val id: Int,
    override val title: String,
    override val posterURL: String,
    override val averageScore: Int,
    val popularity: Int,
    val type: MediaType,
    override val releaseStatus: ReleaseStatus

): SearchResult {

    override val watchbuddyID = WatchbuddyID(API.Anilist, type, id)

    override val primaryDetail = type.toString()
    override val secondaryDetail = "TODO"

    override fun weight() = popularity.toDouble()

    override val progress = weight().toString()
    override val score: Int
        get() = averageScore

}
