package com.gumbachi.watchbuddy.data.remote.tmdb.dto.movie

import kotlinx.serialization.Serializable

@Serializable
data class TMDBMovieSearchResultDTO(
    val id: Int,
    val title: String,
    val poster_path: String? = null,
    val release_date: String = "",
    val popularity: Double = 0.0,
    val vote_average: Double = 0.0,
)
