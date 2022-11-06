package com.gumbachi.watchbuddy.data.remote.tmdb.dto.show

data class TMDBShowSearchResponseDTO(
    val results: List<TMDBShowSearchResultDTO>,
    val total_pages: Int,
    val total_results: Int,
    val page: Int
)