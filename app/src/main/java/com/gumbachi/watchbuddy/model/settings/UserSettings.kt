package com.gumbachi.watchbuddy.model.settings

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

data class UserSettings(
    val general: GeneralSettings = GeneralSettings(),
    val card: CardSettings = CardSettings(),
    val movies: MovieSettings = MovieSettings(),
    val shows: ShowSettings = ShowSettings(),
    val anilist: AnilistSettings = AnilistSettings(),
    val tmdb: TMDBSettings = TMDBSettings()
) {

    fun toRealmUserSettings() = RealmUserSettings(
        generalSettings = general.toRealmGeneralSettings(),
        cardSettings = card.toRealmCardSettings(),
        movieSettings = movies.toRealmMovieSettings(),
        showSettings = shows.toRealmShowSettings(),
        tmdbSettings = tmdb.toRealmTMDBSettings(),
        anilistSettings = anilist.toRealmAnilistSettings()
    )
}


class RealmUserSettings(
    @PrimaryKey var id: Int = 0,
    private var generalSettings: RealmGeneralSettings? = RealmGeneralSettings(),
    private var cardSettings: RealmCardSettings? = RealmCardSettings(),
    private var movieSettings: RealmMovieSettings? = RealmMovieSettings(),
    private var showSettings: RealmShowSettings? = RealmShowSettings(),
    private var tmdbSettings: RealmTMDBSettings? = RealmTMDBSettings(),
    private var anilistSettings: RealmAnilistSettings? = RealmAnilistSettings()
) : RealmObject {

    constructor(): this(id = 0)

    fun toUserSettings() = UserSettings(
        general = generalSettings?.toGeneralSettings() ?: GeneralSettings(),
        card = cardSettings?.toCardSettings() ?: CardSettings(),
        movies = movieSettings?.toMovieSettings() ?: MovieSettings(),
        shows = showSettings?.toShowSettings() ?: ShowSettings(),
        anilist = anilistSettings?.toAnilistSettings() ?: AnilistSettings(),
        tmdb = tmdbSettings?.toTMDBSettings() ?: TMDBSettings()
    )
}

