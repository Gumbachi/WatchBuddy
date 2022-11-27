package com.gumbachi.watchbuddy.data.remote.tmdb.dto.show

import kotlinx.serialization.Serializable

@Serializable
data class TMDBShowDetailsDTO(
    val backdrop_path: String?,
    val episode_run_time: List<Int>,
    val first_air_date: String,
    val poster_path: String?,
    val last_air_date: String,
    val homepage: String,
    val id: Int,
    val in_production: Boolean,
    val languages: List<String>,
    val origin_country: List<String>,
    val popularity: Double,
    val status: String,
    val tagline: String,
    val type: String,
    val vote_count: Long,
    val name: String,
    val number_of_episodes: Int,
    val number_of_seasons: Int,
    val overview: String?,
    val vote_average: Double,
)
