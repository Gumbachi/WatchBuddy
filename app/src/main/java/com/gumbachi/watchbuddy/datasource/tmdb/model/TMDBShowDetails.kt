package com.gumbachi.watchbuddy.datasource.tmdb.model

import com.gumbachi.watchbuddy.model.enums.data.ReleaseStatus
import com.gumbachi.watchbuddy.model.interfaces.MediaDetails
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime

data class TMDBShowDetails(
    override val id: Int,
    override val title: String,
    override val posterURL: String,
    val backdropURL: String,
    val createdBy: List<CreatedBy>,
    val episodeRunTime: List<Int>,

    val firstAirDate: LocalDate?,
    val lastAirDate: LocalDate?,
    val genres: List<Genre>,

    val inProduction: Boolean,
    val languages: List<String>,

    val lastEpisodeToAir: LastEpisodeToAir,

    val networks: List<Network>,
//    val next_episode_to_air: Any? = null,
    val totalEpisodes: Int,
    val totalSeasons: Int,
    val originCountry: List<String>,
    val originalLanguage: String,
    val originalName: String,
    val overview: String,
    val popularity: Double,
//    val production_companies: List<ProductionCompany> = emptyList(),
//    val production_countries: List<ProductionCountry> = emptyList(),
    val seasons: List<Season>,
//    val spoken_languages: List<SpokenLanguage> = emptyList(),
    val status: ReleaseStatus,
    val tagline: String,
    val type: String,
    val averageScore: Double,
    val votes: Long,

    val posters: List<Image>,
    val backdrops: List<Image>,
    val cast: List<Cast>,
    val crew: List<Crew>,
    val reviews: List<Review>,
    val recommendations: List<Recommendation>

) : MediaDetails {

    data class CreatedBy(
        val id: Int,
        val name: String,

        val credit_id: String = "",
//        val gender: Int? = null,
        val profile_path: String? = null
    )

    data class Cast(
        val adult: Boolean = false,
        val character: String = "",
        val creditId: String = "",
//            val gender: Int? = null,
        val id: Int = 0,
        val knownForDepartment: String = "",
        val name: String = "",
        val order: Int = 0,
        val originalName: String = "",
        val popularity: Double = 0.0,
        val imageURL: String = ""
    )

    data class Crew(
        val adult: Boolean = false,
        val creditId: String = "",
//            val gender: Int? = null,
        val id: Int = 0,
        val knownForDepartment: String = "",
        val name: String = "",
        val original_name: String = "",
        val popularity: Double = 0.0,
        val imageURL: String? = null,
        val department: String = "",
        val job: String = ""
    )

    data class Genre(
        val id: Int,
        val name: String
    )

    data class Image(
        val aspectRatio: Double = 0.0,
        val url: String? = null,
        val height: Int = 0,
        val iso_639_1: String? = null,
        val averageScore: Double = 0.0,
        val votes: Long = 0,
        val width: Int = 0
    )

    data class LastEpisodeToAir(
        val id: Int = 0,
        val title: String = "",
        val airDate: LocalDate?,
        val episodeNumber: Int = 0,
        val overview: String = "",
        val productionCode: String = "",
        val runtime: Int = 0,
        val seasonNumber: Int = 0,
        val showId: Int = 0,
        val stillURL: String? = null,
        val averageScore: Double = 0.0,
        val votes: Long = 0
    )

    data class Network(
        val id: Int = 0,
        val logo_path: String? = null,
        val name: String = "",
        val origin_country: String = ""
    )

    data class ProductionCompany(
        val id: Int = 0,
        val logo_path: String? = null,
        val name: String = "",
        val origin_country: String = ""
    )

    data class ProductionCountry(
        val iso_3166_1: String = "",
        val name: String = ""
    )

    data class Recommendation(
        val adult: Boolean = false,
        val backdropURL: String? = null,
        val firstAirDate: LocalDate? = null,
        val genreIDs: List<Int> = emptyList(),
        val id: Int = 0,
        val media_type: String = "",
        val title: String = "",
        val originCountry: List<String> = emptyList(),
        val originalLanguage: String = "",
        val originalName: String = "",
        val overview: String = "",
        val popularity: Double = 0.0,
        val posterURL: String? = null,
        val averageScore: Double = 0.0,
        val votes: Long = 0
    )

    data class Review(
        val id: String,
        val author: String,
        val content: String,
        val authorDetails: AuthorDetails = AuthorDetails(),
        val createdAt: LocalDateTime? = null,
        val updatedAt: LocalDateTime? = null,
        val url: String = ""
    ) {
        data class AuthorDetails(
            val name: String = "Firstname, Lastname",
            val username: String = "Username",
            val avatarURL: String? = null,
            val rating: Double? = null
        )
    }

    data class Season(
        val airDate: String = "",
        val episodeCount: Int = 0,
        val id: Int = 0,
        val name: String = "",
        val overview: String = "",
        val posterURL: String? = null,
        val seasonNumber: Int = 0
    )

    data class SpokenLanguage(
        val english_name: String = "",
        val iso_639_1: String = "",
        val name: String = ""
    )
}


