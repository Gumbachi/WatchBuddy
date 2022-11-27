package com.gumbachi.watchbuddy.model.enums.data

enum class Source(val api: API, val type: MediaType) {
    // TMDB
    TMDBMovie(API.TMDB, MediaType.Movie),
    TMDBShow(API.TMDB, MediaType.Show),

    // AniList
    AnilistMovie(API.Anilist, MediaType.Movie),
    AnilistShow(API.Anilist, MediaType.Show),

    // Custom
    CustomMovie(API.Unknown, MediaType.Movie),
    CustomShow(API.Unknown, MediaType.Show);

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
        fun fromNavArgs(api: String?, type: String?): Source? {
            return when {
                api?.lowercase() == "tmdb" && type?.lowercase() == "movie" -> TMDBMovie
                api?.lowercase() == "tmdb" && type?.lowercase() == "show" -> TMDBShow

                api?.lowercase() == "anilist" && type?.lowercase() == "movie" -> AnilistMovie
                api?.lowercase() == "anilist" && type?.lowercase() == "show" -> AnilistShow

                api?.lowercase() == "custom" && type?.lowercase() == "movie" -> CustomMovie
                api?.lowercase() == "custom" && type?.lowercase() == "show" -> CustomShow

                else -> null
            }
        }
    }
}
