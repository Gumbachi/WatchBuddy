package com.gumbachi.watchbuddy.datasource.tmdb.dto

import kotlinx.serialization.Serializable

@Serializable
data class TMDBMovieDetailsDTO(
    // Required
    val id: Int,
    val title: String,

    // Default Details
    val adult: Boolean = false,
    val backdrop_path: String? = null,
    val budget: Long = 0,
    val genres: List<Genre> = emptyList(),
    val homepage: String? = null,
    val imdb_id: String? = null,
    val original_language: String = "",
    val original_title: String = "",
    val overview: String? = null,
    val popularity: Double = 0.0,
    val poster_path: String? = null,
    val production_companies: List<ProductionCompany> = emptyList(),
    val production_countries: List<ProductionCountry> = emptyList(),
    val release_date: String = "",
    val revenue: Long = 0,
    val runtime: Int? = null,
    val spoken_languages: List<SpokenLanguage> = emptyList(),
    val status: String = "", // allowed values are "Rumored", "Planned", "In Production", "Post Production", "Released", "Canceled"
    val tagline: String? = null,
    val video: Boolean = false,
    val vote_average: Double = 0.0,
    val vote_count: Long = 0,

    // Appended
    val credits: Credits = Credits(),
    val images: Images = Images(),
    val recommendations: Recommendations = Recommendations(),
    val reviews: Reviews = Reviews()
) {
    @Serializable
    data class Genre(
        val id: Int,
        val name: String
    )

    @Serializable
    data class ProductionCompany(
        val id: Int,
        val name: String,
        val logo_path: String? = null,
        val origin_country: String = ""
    )

    @Serializable
    data class ProductionCountry(
        val name: String,
        val iso_3166_1: String = ""
    )

    @Serializable
    data class SpokenLanguage(
        val name: String,
        val iso_639_1: String = ""
    )

    @Serializable
    data class Credits(
        val cast: List<Cast> = emptyList(),
        val crew: List<Crew> = emptyList()
    ) {
        @Serializable
        data class Cast(
            val id: Int,
            val cast_id: Int,
            val credit_id: String,
            val name: String,
            val character: String,

            val adult: Boolean = false,
            val gender: Int? = null,
            val known_for_department: String = "",
            val original_name: String = "",
            val popularity: Double = 0.0,
            val profile_path: String? = null,
            val order: Int = 0
        )

        @Serializable
        data class Crew(
            val id: Int,
            val credit_id: String,
            val name: String,

            val adult: Boolean = false,
            val gender: Int? = null,
            val known_for_department: String = "",
            val original_name: String = "",
            val popularity: Double = 0.0,
            val profile_path: String? = null,
            val department: String = "",
            val job: String = ""
        )
    }

    @Serializable
    data class Images(
        val backdrops: List<Backdrop> = emptyList(),
        val posters: List<Poster> = emptyList()
    ) {
        @Serializable
        data class Backdrop(
            val file_path: String,
            val vote_average: Double = 0.0,
            val vote_count: Long = 0
        )

        @Serializable
        data class Poster(
            val file_path: String,
            val vote_average: Double = 0.0,
            val vote_count: Long = 0
        )
    }

    @Serializable
    data class Recommendations(
        val page: Int = 1,
        val total_pages: Int = 1,
        val total_results: Int = 0,
        val results: List<Result> = emptyList()
    ) {
        @Serializable
        data class Result(
            val id: Int,
            val title: String,
            val poster_path: String? = null,
            val adult: Boolean = false,
            val overview: String = "",
            val release_date: String = "",
            val genre_ids: List<Int> = emptyList(),
            val original_title: String = "",
            val original_language: String = "",
            val backdrop_path: String? = null,
            val popularity: Double = 0.0,
            val vote_count: Long = 0,
            val video: Boolean = false,
            val vote_average: Double = 0.0
        )
    }

    @Serializable
    data class Reviews(
        val page: Int = 1,
        val total_pages: Int = 1,
        val total_results: Int = 0,
        val results: List<Result> = emptyList()
    ) {
        @Serializable
        data class Result(
            val id: String,
            val author: String,
            val content: String,

            val author_details: AuthorDetails = AuthorDetails(),
            val created_at: String = "",
            val updated_at: String = "",
            val url: String = ""
        ) {
            @Serializable
            data class AuthorDetails(
                val name: String = "Firstname, Lastname",
                val username: String = "Username",
                val avatar_path: String? = null,
                val rating: Double? = null
            )
        }
    }
}
