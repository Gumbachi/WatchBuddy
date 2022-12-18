package com.gumbachi.watchbuddy.model

data class OldMediaFilter(
    val includeTMDB: Boolean = true,
    val includeAnilist: Boolean = true,
    val includeMovies: Boolean = true,
    val includeShows: Boolean = true
) {
    val includeTMDBMovies = includeTMDB && includeMovies
    val includeTMDBShows = includeTMDB && includeShows
    val includeAnilistMovies = includeAnilist && includeMovies
    val includeAnilistShows = includeAnilist && includeShows
}

