package com.gumbachi.watchbuddy.data.remote.tmdb.models.show.details

import com.squareup.moshi.Json
import java.time.LocalDate


data class TMDBShowDetails(

    private val backdrop_path: String?,
    private val poster_path: String?,
    private val first_air_date: String,
    private val last_air_date: String,
    private val episode_run_time: List<Int>,

    val created_by: List<CreatedBy>,

    val homepage: String,
    val id: Int,
    val in_production: Boolean,
    val languages: List<String>,
    val last_episode_to_air: LastEpisodeToAir,
    val origin_country: List<String>,
    val popularity: Double,
    val seasons: List<Season>,
    val status: String,
    val tagline: String,
    val type: String,
    val vote_count: Int,

    @field:Json(name = "name")
    val title: String,

    @field:Json(name = "number_of_episodes")
    val totalEpisodes: Int,

    @field:Json(name = "number_of_seasons")
    val totalSeasons: Int,

    @field:Json(name = "overview")
    private val _overview: String?,

    @field:Json(name = "runtime")
    private val _runtime: Int?,

    @field:Json(name = "vote_average")
    val globalScore: Double,
) {

    val startDate = LocalDate.parse(first_air_date)
    val endDate = LocalDate.parse(last_air_date)

    val overview = _overview ?: "Overview Not Available"
    val episodeRuntime = "${episode_run_time.first()}m"


    fun posterURL(): String {
        return poster_path?.let { "https://www.themoviedb.org/t/p/w500$it" } ?: ""
    }

    fun backdropURL(): String {
        return backdrop_path?.let { "https://www.themoviedb.org/t/p/w500$it" } ?: ""
    }
}