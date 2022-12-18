package com.gumbachi.watchbuddy.data.local.realm.mappers


// TODO

//fun RealmMovie.toAnilistMovie(id: Int) = TMDBMovie(
//    id = id,
//    posterURL = posterURL,
//    title = title,
//    releaseDate = LocalDate.ofEpochDay(releaseDate),
//    runtime = runtime,
//    releaseStatus = LocalDate.ofEpochDay(releaseDate).getMovieReleaseStatus(),
//    userScore = userScore,
//    userNotes = userNotes,
//    watchStatus = WatchStatus.valueOf(watchStatus),
//    startDate = startDate?.let { LocalDate.ofEpochDay(it) },
//    finishDate = finishDate?.let { LocalDate.ofEpochDay(it) },
//    lastUpdate = lastUpdate?.let { LocalDateTime.ofEpochSecond(it, 0, ZoneOffset.UTC) }
//)
//
//fun RealmShow.toAnilistShow(id: Int) = AnilistShow(
//    id = id,
//    title = title,
//    posterURL = posterURL,
//    releaseDate = LocalDate.ofEpochDay(releaseDate),
//    releaseStatus = LocalDate.ofEpochDay(releaseDate).getMovieReleaseStatus(),
//    episodesWatched = episodesWatched,
//    totalEpisodes = totalEpisodes,
//    userScore = userScore,
//    userNotes = userNotes,
//    watchStatus = WatchStatus.valueOf(watchStatus),
//    startDate = startDate?.let { LocalDate.ofEpochDay(it) },
//    finishDate = finishDate?.let { LocalDate.ofEpochDay(it) },
//    lastUpdate = lastUpdate?.let { LocalDateTime.ofEpochSecond(it, 0, ZoneOffset.UTC) }
//)