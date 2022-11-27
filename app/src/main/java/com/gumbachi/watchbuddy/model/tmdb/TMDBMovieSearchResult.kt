package com.gumbachi.watchbuddy.model.tmdb

import com.gumbachi.watchbuddy.model.enums.data.Source
import com.gumbachi.watchbuddy.model.interfaces.SearchResult
import com.gumbachi.watchbuddy.utils.getMovieReleaseStatus
import java.time.LocalDate

data class TMDBMovieSearchResult(
    override val id: Int,

    override val title: String,
    override val averageScore: Int,

    override val posterURL: String,
    val releaseDate: LocalDate,

    private val popularity: Double,


    ): SearchResult {

    override val source = Source.TMDBMovie

    override val primaryDetail = "Movie"
    override val secondaryDetail = releaseDate.toString()
    override val releaseStatus = releaseDate.getMovieReleaseStatus()

    override fun weight() = popularity

}
