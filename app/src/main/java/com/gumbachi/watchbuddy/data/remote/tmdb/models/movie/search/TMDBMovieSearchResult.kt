package com.gumbachi.watchbuddy.data.remote.tmdb.models.movie.search

import com.squareup.moshi.Json
import java.time.LocalDate

data class TMDBMovieSearchResult(

    private val poster_path: String?,
    private val release_date: String,

    val id: Int,
    val popularity: Double,
    val title: String,

    @field:Json(name = "vote_average")
    val globalScore: Double
) {

    fun posterURL(): String {
        return poster_path?.let { "https://www.themoviedb.org/t/p/w500$it" } ?: ""
    }

    val releaseDate = LocalDate.parse(release_date)

//
//    private val localDate: LocalDate = LocalDate.parse(release_date)
//
//    override val mediaType: MediaType
//        get() = MediaType.Movie
//
//    override val sourceAPI: SourceAPI
//        get() = SourceAPI.TMDB
//
//    override val releaseStatus: ReleaseStatus
//        get() = ReleaseStatus.fromLocalDate(localDate)
//
//    override fun weight(): Double {
//        return popularity
//    }
//
//    @Composable
//    override fun CompactMediaCard() {
//        CompactMediaCard(
//            imageURL = "https://www.themoviedb.org/t/p/w600_and_h900_bestv2${poster_path}",
//            headline = title,
//            primarySubtitle = "Movie",
//            secondarySubtitle = release_date,
//            score = "$vote_average",
//            status = ReleaseStatus.Unreleased(),
//            progress = "$popularity"
//        )
//    }

}