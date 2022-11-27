package com.gumbachi.watchbuddy.data.remote.tmdb.mappers

import com.gumbachi.watchbuddy.data.remote.tmdb.dto.movie.TMDBMovieDetailsDTO
import com.gumbachi.watchbuddy.data.remote.tmdb.dto.movie.TMDBMovieSearchResponseDTO
import com.gumbachi.watchbuddy.data.remote.tmdb.dto.movie.TMDBMovieSearchResultDTO
import com.gumbachi.watchbuddy.model.tmdb.TMDBMovie
import com.gumbachi.watchbuddy.model.tmdb.TMDBMovieDetails
import com.gumbachi.watchbuddy.model.tmdb.TMDBMovieSearchResult
import com.gumbachi.watchbuddy.utils.getMovieReleaseStatus
import com.gumbachi.watchbuddy.utils.parseDateOrNow
import com.gumbachi.watchbuddy.utils.parseDateOrNull
import kotlin.math.roundToInt


fun TMDBMovieSearchResultDTO.toTMDBMovieSearchResult(): TMDBMovieSearchResult {
    return TMDBMovieSearchResult(
        id = id,
        posterURL = poster_path?.let { "https://www.themoviedb.org/t/p/w500$it" } ?: "",
        averageScore = (vote_average * 10).roundToInt(),
        releaseDate = release_date.parseDateOrNow(),
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
        releaseDate = release_date.parseDateOrNow(),
        runtime = runtime?.let { "${it / 60}h ${it % 60}m" } ?: "??h ??m",
        overview = overview,
        tagline = tagline,
    )
}

fun TMDBMovieDetailsDTO.toTMDBMovie(): TMDBMovie {
    return TMDBMovie(
        id = id,
        posterURL = poster_path?.let { "https://www.themoviedb.org/t/p/w500$it" } ?: "",
        title = title,
        releaseDate = release_date.parseDateOrNow(),
        runtime = runtime?.let { "${it / 60}h ${it % 60}m" } ?: "??h ??m",
        releaseStatus = release_date.parseDateOrNull().getMovieReleaseStatus()
    )
}
