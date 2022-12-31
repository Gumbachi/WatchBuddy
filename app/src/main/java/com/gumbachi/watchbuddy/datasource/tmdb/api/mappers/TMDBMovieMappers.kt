package com.gumbachi.watchbuddy.datasource.tmdb.api.mappers

import com.gumbachi.watchbuddy.datasource.tmdb.api.dto.movie.TMDBMovieDetailsDTO
import com.gumbachi.watchbuddy.datasource.tmdb.api.dto.movie.TMDBMovieSearchResponseDTO
import com.gumbachi.watchbuddy.datasource.tmdb.api.dto.movie.TMDBMovieSearchResultDTO
import com.gumbachi.watchbuddy.datasource.tmdb.model.TMDBMovie
import com.gumbachi.watchbuddy.datasource.tmdb.model.TMDBMovieDetails
import com.gumbachi.watchbuddy.datasource.tmdb.model.TMDBMovieSearchResult
import com.gumbachi.watchbuddy.utils.parseDateOrNull
import kotlin.math.roundToInt


fun TMDBMovieSearchResultDTO.toTMDBMovieSearchResult(): TMDBMovieSearchResult {
    return TMDBMovieSearchResult(
        id = id,
        posterURL = poster_path?.let { "https://www.themoviedb.org/t/p/w500$it" } ?: "",
        averageScore = (vote_average * 10).roundToInt(),
        releaseDate = release_date.parseDateOrNull(),
        popularity = popularity,
        title = title
    )
}

fun TMDBMovieSearchResponseDTO.toTMDBSearchResults(): List<TMDBMovieSearchResult> {
    return results.map { it.toTMDBMovieSearchResult() }
}

fun TMDBMovieDetailsDTO.toTMDBMovieDetails(): TMDBMovieDetails {
    return TMDBMovieDetails(
        backdropURL = backdrop_path?.let { "https://www.themoviedb.org/t/p/w500$it" } ?: "",
        posterURL = poster_path?.let { "https://www.themoviedb.org/t/p/w500$it" } ?: "",
        id = id,
        adult = adult,
        budget = budget,
        revenue = revenue,
        title = title,
        hasVideos = video,
        voteCount = vote_count,
        averageScore = vote_average,
        popularity = popularity,
        releaseDate = release_date.parseDateOrNull(),
        runtime = runtime?.let { "${it / 60}h ${it % 60}m" } ?: "??h ??m",
        overview = overview,
        tagline = tagline,
    )
}

fun TMDBMovieDetails.toTMDBMovie(): TMDBMovie {
    return TMDBMovie(
        id = id,
        posterURL = posterURL,
        title = title,
        releaseDate = releaseDate,
        runtime = runtime
    )
}
