package com.gumbachi.watchbuddy.datasource.tmdb.dto

import kotlinx.serialization.Serializable

@Serializable
data class TMDBMovieSearchResponseDTO(
    val results: List<Result> = emptyList(),
    val total_pages: Int = 0,
    val total_results: Int = 0,
    val page: Int = 1
) {
    @Serializable
    data class Result(
        val id: Int,
        val title: String,

        val poster_path: String? = null,
        val release_date: String = "",
        val popularity: Double = 0.0,
        val vote_average: Double = 0.0
    )
}
