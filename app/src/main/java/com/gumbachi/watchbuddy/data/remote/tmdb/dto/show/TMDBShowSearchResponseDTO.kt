package com.gumbachi.watchbuddy.data.remote.tmdb.dto.show

import kotlinx.serialization.Serializable

@Serializable
data class TMDBShowSearchResponseDTO(
    val results: List<TMDBShowSearchResultDTO>,
    val total_pages: Int,
    val total_results: Int,
    val page: Int
)