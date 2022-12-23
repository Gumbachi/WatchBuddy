package com.gumbachi.watchbuddy.data.local.realm.mappers

import com.gumbachi.watchbuddy.data.local.realm.objects.RealmShow
import com.gumbachi.watchbuddy.model.api.anilist.AnilistShow
import com.gumbachi.watchbuddy.model.api.custom.CustomShow
import com.gumbachi.watchbuddy.model.api.tmdb.TMDBShow
import com.gumbachi.watchbuddy.model.enums.data.API
import com.gumbachi.watchbuddy.model.enums.data.ReleaseStatus
import com.gumbachi.watchbuddy.model.enums.data.WatchStatus
import com.gumbachi.watchbuddy.model.interfaces.Show
import com.gumbachi.watchbuddy.model.toWatchbuddyID
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate

//region RealmShow -> Other
fun RealmShow.toShow(): Show {
    val watchbuddyID = id.toWatchbuddyID()
    return when (watchbuddyID.api) {
        API.TMDB -> toTMDBShow()
        API.Anilist -> toAnilistShow()
        API.Custom -> toCustomShow()
    }
}

fun RealmShow.toTMDBShow(): TMDBShow = TMDBShow(
    id = id.toWatchbuddyID().sourceID,
    posterURL = posterURL,
    title = title,
    releaseDate = releaseDate?.let { LocalDate.fromEpochDays(it) },
    endDate = releaseDate?.let { LocalDate.fromEpochDays(it) },
    episodesWatched = episodesWatched,
    userScore = userScore,
    userNotes = userNotes,
    totalEpisodes = totalEpisodes,
    watchStatus = WatchStatus.valueOf(watchStatus),
    startDate = startDate?.let { LocalDate.fromEpochDays(it) },
    finishDate = finishDate?.let { LocalDate.fromEpochDays(it) },
    lastUpdate = lastUpdate?.let { Instant.fromEpochSeconds(it) }
)

fun RealmShow.toAnilistShow(): AnilistShow = AnilistShow(
    id = id.toWatchbuddyID().sourceID,
    posterURL = posterURL,
    title = title,
    totalEpisodes = totalEpisodes,
    releaseDate = releaseDate?.let { LocalDate.fromEpochDays(it) },
    endDate = releaseDate?.let { LocalDate.fromEpochDays(it) },
    userScore = userScore,
    userNotes = userNotes,
    episodesWatched = episodesWatched,
    watchStatus = WatchStatus.valueOf(watchStatus),
    startDate = startDate?.let { LocalDate.fromEpochDays(it) },
    finishDate = finishDate?.let { LocalDate.fromEpochDays(it) },
    lastUpdate = lastUpdate?.let { Instant.fromEpochSeconds(it) },
    releaseStatus = ReleaseStatus.valueOf(releaseStatus)
)

fun RealmShow.toCustomShow(): CustomShow = CustomShow(
    id = id.toWatchbuddyID().sourceID,
    posterURL = posterURL,
    title = title,
    totalEpisodes = totalEpisodes,
    releaseDate = releaseDate?.let { LocalDate.fromEpochDays(it) },
    endDate = releaseDate?.let { LocalDate.fromEpochDays(it) },
    userScore = userScore,
    episodesWatched = episodesWatched,
    userNotes = userNotes,
    watchStatus = WatchStatus.valueOf(watchStatus),
    startDate = startDate?.let { LocalDate.fromEpochDays(it) },
    finishDate = finishDate?.let { LocalDate.fromEpochDays(it) },
    lastUpdate = lastUpdate?.let { Instant.fromEpochSeconds(it) }
)
//endregion

//region Other -> RealmShow
fun Show.toRealmShow(): RealmShow {
    val show = this
    return RealmShow().apply {
        id = show.watchbuddyID.toString()
        title = show.title
        posterURL = show.posterURL
        releaseDate = show.releaseDate?.toEpochDays()
        watchStatus = show.watchStatus.toString()
        userScore = show.userScore
        userNotes = show.userNotes

        episodesWatched = show.episodesWatched
        totalEpisodes = show.totalEpisodes

        startDate = show.startDate?.toEpochDays()
        finishDate = show.finishDate?.toEpochDays()
        lastUpdate = show.lastUpdate?.epochSeconds

        releaseStatus = show.releaseStatus.toString()
    }
}
//endregion
