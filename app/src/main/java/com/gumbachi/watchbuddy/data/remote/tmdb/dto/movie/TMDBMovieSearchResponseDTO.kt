package com.gumbachi.watchbuddy.data.remote.tmdb.dto.movie

import kotlinx.serialization.Serializable

@Serializable
data class TMDBMovieSearchResponseDTO(
    val results: List<TMDBMovieSearchResultDTO>,
    val total_pages: Int,
    val total_results: Int,
    val page: Int
)
