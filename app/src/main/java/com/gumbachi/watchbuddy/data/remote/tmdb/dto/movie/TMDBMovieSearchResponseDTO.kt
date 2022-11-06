package com.gumbachi.watchbuddy.data.remote.tmdb.dto.movie

data class TMDBMovieSearchResponseDTO(
    val results: List<TMDBMovieSearchResultDTO>,
    val total_pages: Int,
    val total_results: Int,
    val page: Int
)
