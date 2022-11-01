package com.gumbachi.watchbuddy.data.remote.tmdb.models.movie.details

import com.squareup.moshi.Json
import java.time.LocalDate

data class TMDBMovieDetails(
    private val backdrop_path: String?,
    private val poster_path: String?,
    private val release_date: String,

    val id: Int,
    val adult: Boolean,
    val budget: Int,
    val revenue: Int,

    val popularity: Double,

    val status: String,
    val tagline: String?,
    val title: String,
    val video: Boolean,
    val vote_count: Int,

    @field:Json(name = "overview")
    private val _overview: String?,

    @field:Json(name = "runtime")
    private val _runtime: Int?,

    @field:Json(name = "vote_average")
    val globalScore: Double,
) {

    val releaseDate = LocalDate.parse(release_date)
    val overview = _overview ?: "Overview Not Available"
    val runtime = (_runtime ?: "N/A").toString()

    fun posterURL(): String {
        return poster_path?.let { "https://www.themoviedb.org/t/p/w500$it" } ?: ""
    }

    fun backdropURL(): String {
        return backdrop_path?.let { "https://www.themoviedb.org/t/p/w500$it" } ?: ""
    }


}