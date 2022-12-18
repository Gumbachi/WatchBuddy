package com.gumbachi.watchbuddy.model.api.tmdb

import com.gumbachi.watchbuddy.model.interfaces.Detailable
import com.gumbachi.watchbuddy.utils.getMovieReleaseStatus
import java.time.LocalDate

data class TMDBMovieDetails(
    override val backdropURL: String,
    override val posterURL: String,
    val releaseDate: LocalDate,

    override val id: Int,
    val adult: Boolean,
    val budget: Long,
    val revenue: Long,

    override val title: String,
    val hasVideos: Boolean,
    val voteCount: Long,

    override val overview: String?,
    val tagline: String?,

    val runtime: String,

    val popularity: Double,
    val averageScore: Double,
): Detailable {

    val releaseStatus = releaseDate.getMovieReleaseStatus()

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
