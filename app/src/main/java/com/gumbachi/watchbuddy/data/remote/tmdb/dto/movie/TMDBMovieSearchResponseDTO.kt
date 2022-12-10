package com.gumbachi.watchbuddy.data.remote.tmdb.dto.movie

import kotlinx.serialization.Serializable

@Serializable
data class TMDBMovieSearchResponseDTO(
    val results: List<TMDBMovieSearchResultDTO> = emptyList(),
    val total_pages: Int = 0,
    val total_results: Int = 0,
    val page: Int = 1
)
