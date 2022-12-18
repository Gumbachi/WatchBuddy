package com.gumbachi.watchbuddy.model.api.tmdb

import com.gumbachi.watchbuddy.model.WatchbuddyID
import com.gumbachi.watchbuddy.model.enums.data.API
import com.gumbachi.watchbuddy.model.enums.data.MediaType
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

    override val watchbuddyID = WatchbuddyID(API.TMDB, MediaType.Movie, id)

    override val primaryDetail = "Movie"
    override val secondaryDetail = releaseDate.toString()
    override val releaseStatus = releaseDate.getMovieReleaseStatus()

    override fun weight() = popularity

    override val progress = popularity.toString()
    override val score: Int
        get() = averageScore

}
