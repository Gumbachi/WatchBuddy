package com.gumbachi.watchbuddy.datasource.tmdb.model

import com.gumbachi.watchbuddy.model.enums.data.ReleaseStatus
import com.gumbachi.watchbuddy.model.interfaces.Detailable
import com.gumbachi.watchbuddy.utils.toMovieReleaseStatus
import kotlinx.datetime.LocalDate

data class TMDBMovieDetails(
    override val backdropURL: String,
    override val posterURL: String,
    val releaseDate: LocalDate?,

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

    val releaseStatus: ReleaseStatus
        get() = releaseDate.toMovieReleaseStatus()

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
