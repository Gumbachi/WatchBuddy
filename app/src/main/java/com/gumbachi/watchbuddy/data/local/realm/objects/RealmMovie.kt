package com.gumbachi.watchbuddy.data.local.realm.objects

import com.gumbachi.watchbuddy.data.local.realm.mappers.toTMDBMovie
import com.gumbachi.watchbuddy.model.WatchbuddyID
import com.gumbachi.watchbuddy.model.enums.data.API
import com.gumbachi.watchbuddy.model.interfaces.Movie
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import java.time.ZoneOffset

class RealmMovie() : RealmObject {

    @PrimaryKey
    var id = ""

    var title = ""
    var posterURL = ""
    var releaseDate = 0L
    var runtime = ""

    var watchStatus = ""
    var userScore = 0
    var userNotes = ""

    var startDate: Long? = null
    var finishDate: Long? = null
    var lastUpdate: Long? = null

    // Converters
    fun toMovie(): Movie {
        val watchbuddyID = WatchbuddyID from id
        return when (watchbuddyID.api) {
            API.TMDB -> toTMDBMovie(watchbuddyID.sourceID)
            API.Anilist -> TODO()
            API.Custom -> TODO()
        }
    }

    companion object {
        infix fun from(movie: Movie): RealmMovie = RealmMovie().apply {
            id = movie.watchbuddyID.toString()
            title = movie.title
            posterURL = movie.posterURL
            releaseDate = movie.releaseDate.toEpochDay()
            runtime = movie.runtime
            watchStatus = movie.watchStatus.toString()
            userScore = movie.userScore
            userNotes = movie.userNotes
            startDate = movie.startDate?.toEpochDay()
            finishDate = movie.finishDate?.toEpochDay()
            lastUpdate = movie.lastUpdate?.toEpochSecond(ZoneOffset.UTC)
        }
    }
}