package com.gumbachi.watchbuddy.datasource.tmdb.model

import com.gumbachi.watchbuddy.model.interfaces.Detailable
import kotlinx.datetime.LocalDate

data class TMDBShowDetails(
    override val id: Int,
    override val title: String,
    override val overview: String?,
    val firstAirDate: LocalDate?,
    val lastAirDate: LocalDate?,

    val totalSeasons: Int,
    val totalEpisodes: Int,

    override val backdropURL: String,
    override val posterURL: String,

    val popularity: Double,
    val averageScore: Double,
): Detailable {

    override fun shortDetails(): List<String> {
        return listOf(
            "TMDB ID: $id",
            "First Aired: $firstAirDate",
            "Last Aired: $lastAirDate",
            "$totalEpisodes Episodes",
            "$totalSeasons Seasons",
            "Average Score: $averageScore",
            "Popularity: $popularity"
        )
    }
}
