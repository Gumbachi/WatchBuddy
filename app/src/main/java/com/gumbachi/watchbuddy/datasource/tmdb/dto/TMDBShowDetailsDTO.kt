package com.gumbachi.watchbuddy.datasource.tmdb.dto

import kotlinx.serialization.Serializable

@Serializable
data class TMDBShowDetailsDTO(
    val id: Int,
    val name: String,

    val adult: Boolean = false,
    val backdrop_path: String? = null,
    val created_by: List<CreatedBy> = emptyList(),

    val episode_run_time: List<Int> = emptyList(),
    val first_air_date: String = "",
    val genres: List<Genre> = emptyList(),
    val homepage: String = "",


    val in_production: Boolean = false,
    val languages: List<String> = emptyList(),
    val last_air_date: String = "",
    val last_episode_to_air: LastEpisodeToAir = LastEpisodeToAir(),

    val networks: List<Network> = emptyList(),
//    val next_episode_to_air: Any? = null,
    val number_of_episodes: Int = 0,
    val number_of_seasons: Int = 0,
    val origin_country: List<String> = emptyList(),
    val original_language: String = "",
    val original_name: String = "",
    val overview: String = "",
    val popularity: Double = 0.0,
    val poster_path: String? = null,
    val production_companies: List<ProductionCompany> = emptyList(),
    val production_countries: List<ProductionCountry> = emptyList(),


    val seasons: List<Season> = emptyList(),
    val spoken_languages: List<SpokenLanguage> = emptyList(),
    val status: String = "",
    val tagline: String = "",
    val type: String = "",
    val vote_average: Double = 0.0,
    val vote_count: Long = 0,


    val images: Images = Images(),
    val reviews: Reviews = Reviews(),
    val credits: Credits = Credits(),
    val recommendations: Recommendations = Recommendations(),

) {
    @Serializable
    data class CreatedBy(
        val id: Int,
        val name: String,

        val credit_id: String = "",
//        val gender: Int? = null,
        val profile_path: String? = null
    )

    @Serializable
    data class Credits(
        val cast: List<Cast> = emptyList(),
        val crew: List<Crew> = emptyList()
    ) {
        @Serializable
        data class Cast(
            val adult: Boolean = false,
            val character: String = "",
            val credit_id: String = "",
//            val gender: Int? = null,
            val id: Int = 0,
            val known_for_department: String = "",
            val name: String = "",
            val order: Int = 0,
            val original_name: String = "",
            val popularity: Double = 0.0,
            val profile_path: String = ""
        )

        @Serializable
        data class Crew(
            val adult: Boolean = false,
            val credit_id: String = "",
//            val gender: Int? = null,
            val id: Int = 0,
            val known_for_department: String = "",
            val name: String = "",
            val original_name: String = "",
            val popularity: Double = 0.0,
            val profile_path: String? = null,
            val department: String = "",
            val job: String = ""
        )
    }

    @Serializable
    data class Genre(
        val id: Int,
        val name: String
    )

    @Serializable
    data class Images(
        val backdrops: List<Image> = emptyList(),
        val posters: List<Image> = emptyList()
    ) {
        @Serializable
        data class Image(
            val aspect_ratio: Double = 0.0,
            val file_path: String? = null,
            val height: Int = 0,
            val iso_639_1: String? = null,
            val vote_average: Double = 0.0,
            val vote_count: Long = 0,
            val width: Int = 0
        )
    }

    @Serializable
    data class LastEpisodeToAir(
        val id: Int = 0,
        val name: String = "",
        val air_date: String = "",
        val episode_number: Int = 0,
        val overview: String = "",
        val production_code: String = "",
        val runtime: Int = 0,
        val season_number: Int = 0,
        val show_id: Int = 0,
        val still_path: String? = null,
        val vote_average: Double = 0.0,
        val vote_count: Long = 0
    )

    @Serializable
    data class Network(
        val id: Int = 0,
        val logo_path: String? = null,
        val name: String = "",
        val origin_country: String = ""
    )

    @Serializable
    data class ProductionCompany(
        val id: Int = 0,
        val logo_path: String? = null,
        val name: String = "",
        val origin_country: String = ""
    )

    @Serializable
    data class ProductionCountry(
        val iso_3166_1: String = "",
        val name: String = ""
    )

    @Serializable
    data class Recommendations(
        val page: Int = 0,
        val results: List<Result> = emptyList(),
        val total_pages: Int = 0,
        val total_results: Int = 0
    ) {
        @Serializable
        data class Result(
            val adult: Boolean = false,
            val backdrop_path: String? = null,
            val first_air_date: String = "",
            val genre_ids: List<Int> = emptyList(),
            val id: Int = 0,
            val media_type: String = "",
            val name: String = "",
            val origin_country: List<String> = emptyList(),
            val original_language: String = "",
            val original_name: String = "",
            val overview: String = "",
            val popularity: Double = 0.0,
            val poster_path: String? = null,
            val vote_average: Double = 0.0,
            val vote_count: Long = 0
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

    @Serializable
    data class Season(
        val air_date: String = "",
        val episode_count: Int = 0,
        val id: Int = 0,
        val name: String = "",
        val overview: String = "",
        val poster_path: String? = null,
        val season_number: Int = 0
    )

    @Serializable
    data class SpokenLanguage(
        val english_name: String = "",
        val iso_639_1: String = "",
        val name: String = ""
    )
}
