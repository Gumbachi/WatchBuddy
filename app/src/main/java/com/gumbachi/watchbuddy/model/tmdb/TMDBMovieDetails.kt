package com.gumbachi.watchbuddy.model.tmdb

import com.gumbachi.watchbuddy.model.enums.ReleaseStatus
import com.gumbachi.watchbuddy.model.interfaces.Details
import java.time.LocalDate

data class TMDBMovieDetails(
    override val backdropURL: String,
    override val posterURL: String,
    val releaseDate: LocalDate,

    override val id: Int,
    val adult: Boolean,
    val budget: Int,
    val revenue: Int,

    override val title: String,
    val hasVideos: Boolean,
    val voteCount: Int,

    override val overview: String?,
    val tagline: String?,

    val runtime: String,

    val popularity: Double,
    val averageScore: Double,
): Details {

    val releaseStatus = ReleaseStatus.fromDate(releaseDate)

    override fun shortDetails(): List<String> {
        return listOf(
            "TMDB ID: $id",
            "Runtime: $runtime",
            "Release Date: $releaseDate",
            "Average Score: $averageScore",
            "Budget:  $budget",
            "Revenue: $revenue",
            "Popularity: $popularity"
        )
    }
}
