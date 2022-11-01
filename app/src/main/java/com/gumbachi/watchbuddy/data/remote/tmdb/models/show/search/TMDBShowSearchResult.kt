package com.gumbachi.watchbuddy.data.remote.tmdb.models.show.search

import com.squareup.moshi.Json
import java.time.LocalDate

data class TMDBShowSearchResult(
    private val first_air_date: String,
    private val poster_path: String?,

    val id: Int,
    val popularity: Double,

    @field:Json(name = "name")
    val title: String,

    @field:Json(name = "vote_average")
    val globalScore: Double
) {

    fun posterURL(): String {
        return poster_path?.let { "https://www.themoviedb.org/t/p/w500$it" } ?: ""
    }

    val releaseDate = LocalDate.parse(first_air_date)

//    private val localDate: LocalDate = LocalDate.parse(first_air_date)
//
//    override val releaseStatus: ReleaseStatus
//        get() = ReleaseStatus.fromLocalDate(localDate)
//
//    override val mediaType: MediaType
//        get() = MediaType.Show
//
//    override val sourceAPI: SourceAPI
//        get() = SourceAPI.TMDB
//
//    override fun weight(): Double {
//        return popularity
//    }
//
//
//    @Composable
//    override fun CompactMediaCard() {
//        CompactMediaCard(
//            imageURL = "https://www.themoviedb.org/t/p/w600_and_h900_bestv2${poster_path}",
//            headline = name,
//            primarySubtitle = "TV Show",
//            secondarySubtitle = "Air Date",
//            score = "$vote_average",
//            status = ReleaseStatus.Unreleased(),
//            progress = "$popularity"
//        )
//    }
}