package com.gumbachi.watchbuddy.data.remote.tmdb.mappers

import com.gumbachi.watchbuddy.data.remote.tmdb.dto.movie.TMDBMovieDetailsDTO
import com.gumbachi.watchbuddy.data.remote.tmdb.dto.movie.TMDBMovieSearchResponseDTO
import com.gumbachi.watchbuddy.data.remote.tmdb.dto.movie.TMDBMovieSearchResultDTO
import com.gumbachi.watchbuddy.model.tmdb.TMDBMovieDetails
import com.gumbachi.watchbuddy.model.tmdb.TMDBMovieSearchResult
import com.gumbachi.watchbuddy.utils.parseDate


fun TMDBMovieSearchResultDTO.toTMDBMovieSearchResult(): TMDBMovieSearchResult {
    this.apply {
        return TMDBMovieSearchResult(
            id = id,
            posterURL = poster_path?.let { "https://www.themoviedb.org/t/p/w500$it" } ?: "",
            averageScore = vote_average,
            releaseDate = parseDate(release_date),
            popularity = popularity,
            title = title
        )
    }
}

fun TMDBMovieSearchResponseDTO.toTMDBSearchResults(): List<TMDBMovieSearchResult> {
    return this.results.map { it.toTMDBMovieSearchResult() }
}


fun TMDBMovieDetailsDTO.toTMDBMovieDetails(): TMDBMovieDetails {
    this.apply {
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
            releaseDate = parseDate(release_date),
            runtime = runtime?.let { "${it / 60}h ${it % 60}m" } ?: "??h ??m",
            overview = overview,
            tagline = tagline,
        )
    }
}
