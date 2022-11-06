package com.gumbachi.watchbuddy.data.remote.tmdb.dto.show

data class TMDBShowSearchResultDTO(
    val id: Int,
    val name: String,
    val poster_path: String?,
    val first_air_date: String,
    val popularity: Double,
    val vote_average: Double,
)