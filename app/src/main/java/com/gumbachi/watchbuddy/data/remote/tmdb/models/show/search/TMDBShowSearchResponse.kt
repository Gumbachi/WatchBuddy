package com.gumbachi.watchbuddy.data.remote.tmdb.models.show.search

import com.squareup.moshi.Json

data class TMDBShowSearchResponse(
    val results: List<TMDBShowSearchResult>,

    @field:Json(name = "total_pages")
    val totalPages: Int,

    @field:Json(name = "total_results")
    val totalResults: Int,

    val page: Int
)