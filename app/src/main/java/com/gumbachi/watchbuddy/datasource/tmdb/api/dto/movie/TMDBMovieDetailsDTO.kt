package com.gumbachi.watchbuddy.datasource.tmdb.api.dto.movie

import kotlinx.serialization.Serializable

@Serializable
data class TMDBMovieDetailsDTO(

    val id: Int,
    val title: String,

    val backdrop_path: String? = null,
    val poster_path: String? = null,
    val release_date: String = "",
    val adult: Boolean = false,
    val budget: Long = 0,
    val revenue: Long = 0,
    val video: Boolean = false,
    val vote_count: Long = 0,
    val overview: String? = null,
    val tagline: String? = null,
    val runtime: Int? = null,
    val status: String = "",
    val popularity: Double = 0.0,
    val vote_average: Double = 0.0

)