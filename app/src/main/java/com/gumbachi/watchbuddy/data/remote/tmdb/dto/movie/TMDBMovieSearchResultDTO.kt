package com.gumbachi.watchbuddy.data.remote.tmdb.dto.movie

data class TMDBMovieSearchResultDTO(
    val id: Int,
    val title: String,
    val poster_path: String?,
    val release_date: String,
    val popularity: Double,
    val vote_average: Double,
)
