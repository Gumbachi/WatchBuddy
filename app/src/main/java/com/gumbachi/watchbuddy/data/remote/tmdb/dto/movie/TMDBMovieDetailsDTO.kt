package com.gumbachi.watchbuddy.data.remote.tmdb.dto.movie

data class TMDBMovieDetailsDTO(
    val backdrop_path: String?,
    val poster_path: String?,
    val release_date: String,

    val id: Int,
    val adult: Boolean,
    val budget: Long,
    val revenue: Long,

    val title: String,
    val video: Boolean,
    val vote_count: Long,

    val overview: String?,
    val tagline: String?,

    val runtime: Int?,

    val status: String,
    val popularity: Double,
    val vote_average: Double,
)