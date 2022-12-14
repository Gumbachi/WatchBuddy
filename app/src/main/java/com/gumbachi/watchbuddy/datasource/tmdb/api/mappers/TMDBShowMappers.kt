package com.gumbachi.watchbuddy.datasource.tmdb.api.mappers


import com.gumbachi.watchbuddy.datasource.tmdb.api.dto.show.TMDBShowDetailsDTO
import com.gumbachi.watchbuddy.datasource.tmdb.api.dto.show.TMDBShowSearchResponseDTO
import com.gumbachi.watchbuddy.datasource.tmdb.api.dto.show.TMDBShowSearchResultDTO
import com.gumbachi.watchbuddy.datasource.tmdb.model.TMDBShow
import com.gumbachi.watchbuddy.datasource.tmdb.model.TMDBShowDetails
import com.gumbachi.watchbuddy.datasource.tmdb.model.TMDBShowSearchResult
import com.gumbachi.watchbuddy.utils.parseDateOrNull
import kotlin.math.roundToInt

fun TMDBShowSearchResultDTO.toTMDBShowSearchResult(): TMDBShowSearchResult {
    return TMDBShowSearchResult(
        id = id,
        title = name,
        posterURL = poster_path?.let { "https://www.themoviedb.org/t/p/w500$it" } ?: "",
        airDate = first_air_date.parseDateOrNull(),
        averageScore = (vote_average * 10).roundToInt(),
        popularity = popularity
    )
}

fun TMDBShowSearchResponseDTO.toTMDBSearchResults(): List<TMDBShowSearchResult> {
    return results.map { it.toTMDBShowSearchResult() }
}

fun TMDBShowDetailsDTO.toTMDBShowDetails(): TMDBShowDetails {
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
        firstAirDate = first_air_date.parseDateOrNull(),
        lastAirDate = last_air_date.parseDateOrNull()
    )
}

fun TMDBShowDetails.toTMDBShow(): TMDBShow {
    return TMDBShow(
        id = id,
        posterURL = posterURL,
        title = title,
        releaseDate = firstAirDate,
        endDate = lastAirDate,
        totalEpisodes = totalEpisodes,
    )
}