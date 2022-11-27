package com.gumbachi.watchbuddy.data.remote.tmdb.dto.show

import kotlinx.serialization.Serializable

@Serializable
data class TMDBShowSearchResultDTO(
    val id: Int,
    val name: String,
    val poster_path: String? = "",
    val first_air_date: String = "",
    val popularity: Double = 0.0,
    val vote_average: Double = 0.0,
)