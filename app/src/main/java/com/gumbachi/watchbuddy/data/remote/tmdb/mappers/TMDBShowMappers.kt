package com.gumbachi.watchbuddy.data.remote.tmdb.mappers

import com.gumbachi.watchbuddy.data.remote.tmdb.dto.show.TMDBShowDetailsDTO
import com.gumbachi.watchbuddy.data.remote.tmdb.dto.show.TMDBShowSearchResponseDTO
import com.gumbachi.watchbuddy.data.remote.tmdb.dto.show.TMDBShowSearchResultDTO
import com.gumbachi.watchbuddy.model.tmdb.TMDBShowDetails
import com.gumbachi.watchbuddy.model.tmdb.TMDBShowSearchResult
import com.gumbachi.watchbuddy.utils.parseDate

fun TMDBShowSearchResultDTO.toTMDBShowSearchResult(): TMDBShowSearchResult {
    this.apply {
        return TMDBShowSearchResult(
            id = id,
            title = name,
            posterURL = poster_path?.let { "https://www.themoviedb.org/t/p/w500$it" } ?: "",
            airDate = parseDate(first_air_date),
            averageScore = vote_average,
            popularity = popularity
        )
    }
}

fun TMDBShowSearchResponseDTO.toTMDBSearchResults(): List<TMDBShowSearchResult> {
    return this.results.map { it.toTMDBShowSearchResult() }
}

fun TMDBShowDetailsDTO.toTMDBShowDetails(): TMDBShowDetails {
    this.apply {
        return TMDBShowDetails(
            id = id,
            title = name,
            overview = overview,
            totalEpisodes = number_of_episodes,
            totalSeasons = number_of_seasons,
            popularity = popularity,
            averageScore = vote_average,
            backdropURL = backdrop_path?.let { "https://www.themoviedb.org/t/p/w500$it" } ?: "",
            posterURL = poster_path?.let { "https://www.themoviedb.org/t/p/w500$it" } ?: "",
            firstAirDate = parseDate(first_air_date),
            lastAirDate = parseDate(last_air_date)
        )
    }
}