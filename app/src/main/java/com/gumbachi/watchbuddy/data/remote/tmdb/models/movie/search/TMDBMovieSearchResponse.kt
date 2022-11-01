package com.gumbachi.watchbuddy.data.remote.tmdb.models.movie.search

import com.squareup.moshi.Json

data class TMDBMovieSearchResponse(
    @field:Json(name = "results")
    val searchResults: List<TMDBMovieSearchResult>,

    @field:Json(name = "total_pages")
    val totalPages: Int,

    @field:Json(name = "total_results")
    val totalResults: Int,

    val page: Int
)