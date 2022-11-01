package com.gumbachi.watchbuddy.data.remote.tmdb.models.show.details

import com.squareup.moshi.Json
import java.time.LocalDate

data class Season(
    private val air_date: String,

    val id: Int,
    val name: String,
    val overview: String,

    private val poster_path: String,

    @field:Json(name = "episode_count")
    val totalEpisodes: Int,

    @field:Json(name = "season_number")
    val seasonNumber: Int
) {

    val releaseDate = LocalDate.parse(air_date)

    fun posterURL(): String {
        return "https://www.themoviedb.org/t/p/w500$poster_path"
    }
}
