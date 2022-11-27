package com.gumbachi.watchbuddy.model

data class SearchFilter(
    val tmdb: Boolean = true,
    val anilist: Boolean = true,
    val movies: Boolean = true,
    val shows: Boolean = true
) {
    val tmdbMovies = tmdb && movies
    val tmdbShows = tmdb && shows
}

