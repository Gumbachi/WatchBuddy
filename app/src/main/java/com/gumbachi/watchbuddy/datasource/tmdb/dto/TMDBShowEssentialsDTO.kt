package com.gumbachi.watchbuddy.datasource.tmdb.dto

import kotlinx.serialization.Serializable

@Serializable
data class TMDBShowEssentialsDTO(
    val id: Int,
    val name: String,

    val episode_run_time: List<Int> = emptyList(),
    val first_air_date: String = "",
    val poster_path: String? = null,
    val last_air_date: String = "",

    val popularity: Double = 0.0,
    val status: String = "",
    val number_of_episodes: Int = 0,
    val number_of_seasons: Int = 1,
    val vote_average: Double = 0.0
)