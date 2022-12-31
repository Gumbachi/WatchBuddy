package com.gumbachi.watchbuddy.datasource.tmdb.api.dto.show

import kotlinx.serialization.Serializable

@Serializable
data class TMDBShowDetailsDTO(

    val id: Int,
    val name: String,

    val backdrop_path: String? = null,
    val episode_run_time: List<Int> = emptyList(),
    val first_air_date: String = "",
    val poster_path: String? = null,
    val last_air_date: String = "",
    val homepage: String = "",
    val in_production: Boolean = false,
    val languages: List<String> = emptyList(),
    val origin_country: List<String> = emptyList(),
    val popularity: Double = 0.0,
    val status: String = "",
    val tagline: String = "",
    val type: String = "",
    val vote_count: Long = 0,
    val number_of_episodes: Int = 0,
    val number_of_seasons: Int = 1,
    val overview: String? = null,
    val vote_average: Double = 0.0

)
