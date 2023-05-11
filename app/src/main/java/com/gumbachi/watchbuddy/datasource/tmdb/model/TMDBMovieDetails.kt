package com.gumbachi.watchbuddy.datasource.tmdb.model

import com.gumbachi.watchbuddy.model.WatchBuddyID
import com.gumbachi.watchbuddy.model.enums.data.API
import com.gumbachi.watchbuddy.model.enums.data.MediaType
import com.gumbachi.watchbuddy.model.enums.data.ReleaseStatus
import com.gumbachi.watchbuddy.model.interfaces.MediaDetails
import com.gumbachi.watchbuddy.model.interfaces.Review
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime

data class TMDBMovieDetails(
    // Required
    override val id: Int,
    override val title: String,
    override val posterURL: String,

    // Default Details
    val adult: Boolean,
    val backdropURL: String,
    val budget: Long,
    val genres: List<Genre>,
    val homepage: String?,
    val imdbID: String?,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String?,
    val popularity: Double,

//    val productionCompanies: List<ProductionCompany>,
//    val productionCountries: List<ProductionCountry>,
    val releaseDate: LocalDate?,
    val revenue: Long,
    val runtime: Int?,
//    val spokenLanguages: List<SpokenLanguage>,
    val status: ReleaseStatus,
    val tagline: String?,
    val video: Boolean,
    val globalScore: Double,
    val votes: Long,

    // Credits
    val cast: List<Cast>,
    val crew: List<Crew>,

    // Images
    val backdrops: List<Image>,
    val posters: List<Image>,

    // Recommendations and Reviews
    val recommendations: List<Recommendation>,
    val reviews: List<UserReview>
): MediaDetails {

    val formattedRuntime = runtime?.let { "${it / 60}h ${it % 60}m" } ?: "??h ??m"

    override val watchBuddyID = WatchBuddyID(API.TMDB, MediaType.Movie, id)

    data class Genre(
        val id: Int,
        val name: String
    )

    data class ProductionCompany(
        val id: Int,
        val name: String,
        val logoURL: String,
        val originCountry: String,
    )

    data class ProductionCountry(
        val name: String,
        val iso_3166_1: String = ""
    )

    data class SpokenLanguage(
        val name: String,
        val iso_639_1: String = ""
    )

    data class Cast(
        val id: Int,
        val castID: Int,
        val creditID: String,
        val name: String,
        val character: String,
//        val adult: Boolean = false,
//        val gender: Int? = null,
        val knownFor: String,
        val originalName: String,
        val popularity: Double,
        val profileURL: String,
        val order: Int
    )

    data class Crew(
        val id: Int,
        val credit_id: String,
        val name: String,

//        val adult: Boolean,
//        val gender: Int?,
        val knownFor: String,
        val originalName: String,
        val popularity: Double,
        val profileURL: String,
        val department: String,
        val job: String
    )

    data class Image(
        val url: String,
        val score: Double,
        val votes: Long
    )

    data class Recommendation(
        val id: Int,
        val title: String,
        val posterURL: String,
//        val adult: Boolean = false,
//        val overview: String = "",
        val releaseDate: LocalDate?,
        val genreIDs: List<Int>,
//        val originalTitle: String = "",
//        val original_language: String = "",
//        val backdrop_path: String? = null,
        val popularity: Double,
//        val vote_count: Long = 0,
//        val video: Boolean = false,
        val userScore: Double
    )

    data class UserReview(
        val id: String,
        override val author: String,
        override val content: String,

        val authorName: String,
        val authorUsername: String,
        override val avatarURL: String,
        override val rating: Int?,

        val createdAt: LocalDateTime?,
        val updatedAt: LocalDateTime?,
        val url: String
    ) : Review
}


