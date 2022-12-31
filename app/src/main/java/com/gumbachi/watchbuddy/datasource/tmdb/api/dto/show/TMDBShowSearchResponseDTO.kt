package com.gumbachi.watchbuddy.datasource.tmdb.api.dto.show

import kotlinx.serialization.Serializable

@Serializable
data class TMDBShowSearchResponseDTO(
    val results: List<TMDBShowSearchResultDTO> = emptyList(),
    val total_pages: Int = 0,
    val total_results: Int = 0,
    val page: Int = 0
)