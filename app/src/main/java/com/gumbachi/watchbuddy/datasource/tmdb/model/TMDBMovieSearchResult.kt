package com.gumbachi.watchbuddy.datasource.tmdb.model

import com.gumbachi.watchbuddy.model.WatchBuddyID
import com.gumbachi.watchbuddy.model.enums.data.API
import com.gumbachi.watchbuddy.model.enums.data.MediaType
import com.gumbachi.watchbuddy.model.enums.data.ReleaseStatus
import com.gumbachi.watchbuddy.model.interfaces.SearchResult
import com.gumbachi.watchbuddy.utils.toMovieReleaseStatus
import kotlinx.datetime.LocalDate

data class TMDBMovieSearchResult(
    override val id: Int,
    override val title: String,
    override val averageScore: Int,
    override val posterURL: String,
    val releaseDate: LocalDate?,
    private val popularity: Double
) : SearchResult {

    override val watchbuddyID = WatchBuddyID(API.TMDB, MediaType.Movie, id)

    override val primaryDetail = "Movie"
    override val secondaryDetail = releaseDate.toString()
    override val releaseStatus: ReleaseStatus
        get() = releaseDate.toMovieReleaseStatus()

    override fun weight() = popularity

    override val progress = popularity.toString()
    override val score: Int
        get() = averageScore

}
