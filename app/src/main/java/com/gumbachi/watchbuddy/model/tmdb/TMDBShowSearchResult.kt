package com.gumbachi.watchbuddy.model.tmdb

import com.gumbachi.watchbuddy.model.enums.MediaType
import com.gumbachi.watchbuddy.model.enums.ReleaseStatus
import com.gumbachi.watchbuddy.model.enums.SourceAPI
import com.gumbachi.watchbuddy.model.interfaces.SearchResult
import java.time.LocalDate

data class TMDBShowSearchResult(
    override val api: SourceAPI = SourceAPI.TMDB,
    override val type: MediaType = MediaType.Show,
    override val id: Int,

    override val title: String,
    override val posterURL: String,
    val airDate: LocalDate,

    override val averageScore: Double,
    private val popularity: Double,


): SearchResult {

    override val releaseStatus = ReleaseStatus.fromDate(airDate)
    override val primaryDetail = "TV Show"
    override val secondaryDetail = "Aired: $airDate"

    override fun weight() = popularity
}
