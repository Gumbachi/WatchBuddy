package com.gumbachi.watchbuddy.data.remote.tmdb.dto.show

import kotlinx.serialization.Serializable

@Serializable
data class TMDBShowDetailsDTO(
    val backdrop_path: String? = null,
    val episode_run_time: List<Int> = emptyList(),
    val first_air_date: String = "",
    val poster_path: String? = null,
    val last_air_date: String = "",
    val homepage: String = "",
    val id: Int,
    val in_production: Boolean = false,
    val languages: List<String> = emptyList(),
    val origin_country: List<String> = emptyList(),
    val popularity: Double = 0.0,
    val status: String = "",
    val tagline: String = "",
    val type: String = "",
    val vote_count: Long = 0,
    val name: String = "",
    val number_of_episodes: Int = 0,
    val number_of_seasons: Int = 1,
    val overview: String? = null,
    val vote_average: Double = 0.0,
)
