package com.gumbachi.watchbuddy.datasource.tmdb.mappers


import com.gumbachi.watchbuddy.datasource.tmdb.dto.TMDBShowDetailsDTO
import com.gumbachi.watchbuddy.datasource.tmdb.dto.TMDBShowEssentialsDTO
import com.gumbachi.watchbuddy.datasource.tmdb.dto.TMDBShowSearchResponseDTO
import com.gumbachi.watchbuddy.datasource.tmdb.model.TMDBShow
import com.gumbachi.watchbuddy.datasource.tmdb.model.TMDBShowDetails
import com.gumbachi.watchbuddy.datasource.tmdb.model.TMDBShowSearchResult
import com.gumbachi.watchbuddy.model.enums.data.ReleaseStatus
import com.gumbachi.watchbuddy.utils.MediaDummy.NormalShow.totalEpisodes
import com.gumbachi.watchbuddy.utils.parseDateOrNull
import com.gumbachi.watchbuddy.utils.parseDateTimeOrNull
import com.gumbachi.watchbuddy.utils.toTMDBImageURL
import com.gumbachi.watchbuddy.utils.toTMDBImageURLOrBlank
import kotlin.math.roundToInt

fun TMDBShowSearchResponseDTO.Result.toTMDBShowSearchResult() = TMDBShowSearchResult(
    id = id,
    title = name,
    posterURL = poster_path.toTMDBImageURLOrBlank(),
    airDate = first_air_date.parseDateOrNull(),
    averageScore = (vote_average * 10).roundToInt(),
    popularity = popularity
)

fun TMDBShowSearchResponseDTO.toTMDBSearchResults() = results.map { it.toTMDBShowSearchResult() }

fun TMDBShowDetailsDTO.toTMDBShowDetails() = TMDBShowDetails(
    id = id,
    title = name,
    posterURL = poster_path.toTMDBImageURLOrBlank(),
    backdropURL = backdrop_path.toTMDBImageURLOrBlank(),
    createdBy = created_by.map {
        TMDBShowDetails.CreatedBy(
            id = it.id,
            name = it.name,
            credit_id = it.credit_id,
            profile_path = it.profile_path.toTMDBImageURLOrBlank()

        )
    },
    episodeRunTime = episode_run_time,
    firstAirDate = first_air_date.parseDateOrNull(),
    lastAirDate = last_air_date.parseDateOrNull(),
    genres = genres.map { TMDBShowDetails.Genre(id = it.id, name = it.name) },
    inProduction = in_production,
    languages = languages,
    lastEpisodeToAir = with(last_episode_to_air) {
        TMDBShowDetails.LastEpisodeToAir(
            id = id,
            title = name,
            airDate = air_date.parseDateOrNull(),
            episodeNumber = episode_number,
            overview = overview,
            productionCode = production_code,
            runtime = runtime,
            seasonNumber = season_number,
            showId = show_id,
            stillURL = still_path.toTMDBImageURLOrBlank(),
            averageScore = vote_average,
            votes = vote_count,
        )
    },
    networks = networks.map {
        TMDBShowDetails.Network(
            id = it.id,
            logo_path = it.logo_path.toTMDBImageURLOrBlank(),
            name = it.name,
            origin_country = it.origin_country
        )
    },
    totalEpisodes = number_of_episodes,
    totalSeasons = number_of_seasons,
    originCountry = origin_country,
    originalLanguage = original_language,
    originalName = original_name,
    overview = overview,
    popularity = popularity,
    seasons = seasons.map {
        TMDBShowDetails.Season(
            airDate = it.air_date.toTMDBImageURL(),
            episodeCount = it.episode_count,
            id = it.id,
            name = it.name,
            overview = it.overview,
            posterURL = it.poster_path.toTMDBImageURLOrBlank(),
            seasonNumber = it.season_number

        )
    },
    status = when (status) {
        else -> {
            println("Show Status -> $status")
            ReleaseStatus.Unknown
        }
    },
    tagline = tagline,
    type = run {
        println("Type is -> $type")
        type
    },
    averageScore = vote_average,
    votes = vote_count,
    posters = images.posters.map {
        TMDBShowDetails.Image(
            aspectRatio = it.aspect_ratio,
            url = it.file_path.toTMDBImageURLOrBlank(),
            height = it.height,
            iso_639_1 = it.iso_639_1,
            averageScore = it.vote_average,
            votes = it.vote_count,
            width = it.width
        )
    },
    backdrops = images.backdrops.map {
        TMDBShowDetails.Image(
            aspectRatio = it.aspect_ratio,
            url = it.file_path.toTMDBImageURLOrBlank(),
            height = it.height,
            iso_639_1 = it.iso_639_1,
            averageScore = it.vote_average,
            votes = it.vote_count,
            width = it.width
        )
    },
    cast = credits.cast.map {
        TMDBShowDetails.Cast(
            adult = it.adult,
            character = it.character,
            creditId = it.credit_id,
            id = it.id,
            knownForDepartment = it.known_for_department,
            name = it.name,
            order = it.order,
            originalName = it.original_name,
            popularity = it.popularity,
            imageURL = it.profile_path.toTMDBImageURLOrBlank()
        )
    },
    crew = credits.crew.map {
        TMDBShowDetails.Crew(
            adult = it.adult,
            creditId = it.credit_id,
            id = it.id,
            knownForDepartment = it.known_for_department,
            name = it.name,
            original_name = it.original_name,
            popularity = it.popularity,
            imageURL = it.profile_path.toTMDBImageURLOrBlank(),
            department = it.department,
            job = it.job
        )
    },
    reviews = reviews.results.map {
        TMDBShowDetails.Review(
            id = it.id,
            author = it.author,
            content = it.content,
            authorDetails = TMDBShowDetails.Review.AuthorDetails(
                name = it.author_details.name,
                username = it.author_details.username,
                avatarURL = it.author_details.avatar_path.toTMDBImageURLOrBlank(),
                rating = it.author_details.rating
            ),
            createdAt = it.created_at.parseDateTimeOrNull(),
            updatedAt = it.updated_at.parseDateTimeOrNull(),
            url = it.url
        )
    },
    recommendations = recommendations.results.map {
        TMDBShowDetails.Recommendation(
            adult = it.adult,
            backdropURL = it.backdrop_path.toTMDBImageURLOrBlank(),
            firstAirDate = it.first_air_date.parseDateOrNull(),
            genreIDs = it.genre_ids,
            id = it.id,
            media_type = run {
                println("Media Type for recommendation is -> ${it.media_type}")
                it.media_type
            },
            title = it.name,
            originCountry = it.origin_country,
            originalLanguage = it.original_language,
            originalName = it.original_name,
            overview = it.overview,
            popularity = it.popularity,
            posterURL = it.poster_path.toTMDBImageURLOrBlank(),
            averageScore = it.vote_average,
            votes = vote_count
        )
    }
)

fun TMDBShowEssentialsDTO.toTMDBShow() = TMDBShow(
    id = id,
    posterURL = poster_path.toTMDBImageURLOrBlank(),
    title = this.name,
    releaseDate = first_air_date.parseDateOrNull(),
    endDate = last_air_date.parseDateOrNull(),
    totalEpisodes = totalEpisodes,
)
