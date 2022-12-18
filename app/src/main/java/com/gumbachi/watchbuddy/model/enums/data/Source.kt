package com.gumbachi.watchbuddy.model.enums.data


enum class Source(val api: API, val type: MediaType) {
    // TMDB
    TMDBMovie(API.TMDB, MediaType.Movie),
    TMDBShow(API.TMDB, MediaType.Show),

    // AniList
    AnilistMovie(API.Anilist, MediaType.Movie),
    AnilistShow(API.Anilist, MediaType.Show),

    // Custom
    CustomMovie(API.Custom, MediaType.Movie),
    CustomShow(API.Custom, MediaType.Show);

    fun toDetailsRoute(id: Int): String {
        return when (this) {
            TMDBMovie -> "tmdb/movie/$id"
            TMDBShow -> "tmdb/show/$id"
            AnilistMovie -> "anilist/movie/$id"
            AnilistShow -> "anilist/show/$id"
            CustomMovie -> "custom/movie/$id"
            CustomShow -> "custom/show/$id"
        }
    }

    companion object {

        fun from(api: API, type: MediaType) = when {
            api == API.TMDB && type == MediaType.Movie -> TMDBMovie
            api == API.TMDB && type == MediaType.Show -> TMDBShow

            api == API.Anilist && type == MediaType.Movie -> AnilistMovie
            api == API.Anilist && type == MediaType.Show -> AnilistShow

            api == API.Custom && type == MediaType.Movie -> CustomMovie
            api == API.Custom && type == MediaType.Show -> CustomShow

            else -> throw Exception("Couldn't Find Source Type for $api and $type")
        }
    }
}
