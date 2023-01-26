package com.gumbachi.watchbuddy.datasource.tmdb.dto

import kotlinx.serialization.Serializable

/**Class with data required to construct an watchbuddy object*/
@Serializable
data class TMDBMovieEssentialsDTO(

    val id: Int,
    val title: String,

    val poster_path: String? = null,
    val release_date: String = "",
    val adult: Boolean = false,
    val runtime: Int? = null,
    val status: String = "",
    val popularity: Double = 0.0,
    val vote_average: Double = 0.0

)