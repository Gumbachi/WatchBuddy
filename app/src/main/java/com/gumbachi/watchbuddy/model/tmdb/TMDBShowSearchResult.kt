package com.gumbachi.watchbuddy.model.tmdb

import com.gumbachi.watchbuddy.model.WatchbuddyID
import com.gumbachi.watchbuddy.model.enums.data.API
import com.gumbachi.watchbuddy.model.enums.data.MediaType
import com.gumbachi.watchbuddy.model.enums.data.ReleaseStatus
import com.gumbachi.watchbuddy.model.interfaces.SearchResult
import java.time.LocalDate

data class TMDBShowSearchResult(
    override val id: Int,

    override val title: String,
    override val posterURL: String,
    val airDate: LocalDate,

    override val averageScore: Int,
    private val popularity: Double,

    ): SearchResult {

    override val watchbuddyID = WatchbuddyID(API.TMDB, MediaType.Show, id)

    override val releaseStatus = ReleaseStatus.Unknown() // TODO Fix this
    override val primaryDetail = "TV Show"
    override val secondaryDetail = "Aired: $airDate"

    override fun weight() = popularity

    override val progress = null
    override val score: Int
        get() = averageScore
}
