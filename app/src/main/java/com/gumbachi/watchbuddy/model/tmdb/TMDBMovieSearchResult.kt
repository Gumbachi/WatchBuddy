package com.gumbachi.watchbuddy.model.tmdb

import com.gumbachi.watchbuddy.model.enums.MediaType
import com.gumbachi.watchbuddy.model.enums.ReleaseStatus
import com.gumbachi.watchbuddy.model.enums.SourceAPI
import com.gumbachi.watchbuddy.model.interfaces.SearchResult
import java.time.LocalDate

data class TMDBMovieSearchResult(
    override val id: Int,
    override val api: SourceAPI = SourceAPI.TMDB,
    override val type: MediaType = MediaType.Movie,

    override val title: String,
    override val averageScore: Double,

    override val posterURL: String,
    val releaseDate: LocalDate,

    private val popularity: Double,


    ): SearchResult {

    override val primaryDetail = "Movie"
    override val secondaryDetail = releaseDate.toString()
    override val releaseStatus = ReleaseStatus.fromDate(releaseDate)

    override fun weight() = popularity
}
