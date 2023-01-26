package com.gumbachi.watchbuddy.datasource.tmdb.mappers

import com.gumbachi.watchbuddy.datasource.tmdb.dto.TMDBMovieDetailsDTO
import com.gumbachi.watchbuddy.datasource.tmdb.dto.TMDBMovieEssentialsDTO
import com.gumbachi.watchbuddy.datasource.tmdb.dto.TMDBMovieSearchResponseDTO
import com.gumbachi.watchbuddy.datasource.tmdb.model.TMDBMovie
import com.gumbachi.watchbuddy.datasource.tmdb.model.TMDBMovieDetails
import com.gumbachi.watchbuddy.datasource.tmdb.model.TMDBMovieSearchResult
import com.gumbachi.watchbuddy.model.enums.data.ReleaseStatus
import com.gumbachi.watchbuddy.utils.*
import kotlin.math.roundToInt

fun TMDBMovieSearchResponseDTO.Result.toTMDBMovieSearchResult() = TMDBMovieSearchResult(
    id = id,
    posterURL = poster_path.toTMDBImageURLOrBlank(),
    averageScore = (vote_average * 10).roundToInt(),
    releaseDate = release_date.parseDateOrNull(),
    popularity = popularity,
    title = title
)

fun TMDBMovieSearchResponseDTO.toTMDBSearchResults() = results.map { it.toTMDBMovieSearchResult() }

fun TMDBMovieEssentialsDTO.toTMDBMovie() = TMDBMovie(
    id = id,
    posterURL = poster_path.toTMDBImageURLOrBlank(),
    title = title,
    releaseDate = release_date.parseDateOrNull(),
    runtime = runtime.formatRuntime(),
)

fun TMDBMovieDetailsDTO.toTMDBMovieDetails() = TMDBMovieDetails(
    id = id,
    title = title,
    backdropURL = backdrop_path.toTMDBImageURLOrBlank(),
    posterURL = poster_path.toTMDBImageURLOrBlank(),
    adult = adult,
    budget = budget,
    revenue = revenue,
    votes = vote_count,
    globalScore = vote_average,
    popularity = popularity,
    releaseDate = release_date.parseDateOrNull(),
    runtime = runtime,
    overview = overview,
    tagline = tagline,
    genres = genres.map { TMDBMovieDetails.Genre(it.id, it.name) },
    homepage = homepage,
    imdbID = imdb_id,
    originalLanguage = original_language,
    originalTitle = original_title,
    status = when (status) {
        "Released" -> ReleaseStatus.Released
        "Canceled" -> ReleaseStatus.Cancelled
        "Rumored", "Planned", "In Production", "Post Production" -> ReleaseStatus.Unreleased
        else -> ReleaseStatus.Unknown
    },
    video = video,
    cast = credits.cast.map {
        TMDBMovieDetails.Cast(
            id = it.id,
            castID = it.cast_id,
            creditID = it.credit_id,
            name = it.name,
            character = it.character,
            knownFor = it.known_for_department,
            originalName = it.original_name,
            popularity = it.popularity,
            profileURL = it.profile_path.toTMDBImageURLOrBlank(),
            order = it.order
        )
    },
    crew = credits.crew.map {
        TMDBMovieDetails.Crew(
            id = it.id,
            credit_id = it.credit_id,
            name = it.name,
            knownFor = it.known_for_department,
            originalName = it.original_name,
            popularity = it.popularity,
            profileURL = it.profile_path.toTMDBImageURLOrBlank(),
            job = it.job,
            department = it.known_for_department
        )
    },
    backdrops = images.backdrops.map {
        TMDBMovieDetails.Image(
            url = it.file_path.toTMDBImageURL(),
            score = it.vote_average,
            votes = it.vote_count
        )
    },
    posters = images.posters.map {
        TMDBMovieDetails.Image(
            url = it.file_path.toTMDBImageURL(),
            score = it.vote_average,
            votes = it.vote_count
        )
    },
    recommendations = recommendations.results.map {
        TMDBMovieDetails.Recommendation(
            id = it.id,
            title = it.title,
            posterURL = it.poster_path.toTMDBImageURLOrBlank(),
            releaseDate = it.release_date.parseDateOrNull(),
            genreIDs = it.genre_ids,
            popularity = it.popularity,
            userScore = it.vote_average
        )
    },
    reviews = reviews.results.map {
        TMDBMovieDetails.Review(
            id = it.id,
            author = it.author,
            content = it.content,
            authorName = it.author_details.name,
            authorUsername = it.author_details.username,
            authorAvatarURL = it.author_details.avatar_path.toTMDBImageURLOrBlank(),
            rating = it.author_details.rating,
            createdAt = it.created_at.parseDateTimeOrNull(),
            updatedAt = it.updated_at.parseDateTimeOrNull(),
            url = it.url
        )
    },
)
