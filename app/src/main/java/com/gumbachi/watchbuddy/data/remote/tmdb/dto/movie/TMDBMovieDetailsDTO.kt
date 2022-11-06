package com.gumbachi.watchbuddy.data.remote.tmdb.dto.movie

data class TMDBMovieDetailsDTO(
    val backdrop_path: String?,
    val poster_path: String?,
    val release_date: String,

    val id: Int,
    val adult: Boolean,
    val budget: Int,
    val revenue: Int,

    val title: String,
    val video: Boolean,
    val vote_count: Int,

    val overview: String?,
    val tagline: String?,

    val runtime: Int?,

    val status: String,
    val popularity: Double,
    val vote_average: Double,
)