package com.gumbachi.watchbuddy.data.local.realm.objects

import com.gumbachi.watchbuddy.data.local.realm.mappers.toTMDBMovie
import com.gumbachi.watchbuddy.model.enums.data.Source
import com.gumbachi.watchbuddy.model.interfaces.Movie
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import java.time.ZoneOffset

class RealmMovie() : RealmObject {

    constructor(movie: Movie) : this() {
        id = movie.qualifiedID

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
        val split = id.split("|", limit = 2)
        val source = Source.valueOf(split.first())
        val sourceID = split.last().toInt()

        return when (source) {
            Source.TMDBMovie -> toTMDBMovie(sourceID)
            Source.TMDBShow -> TODO()
            Source.AnilistMovie -> TODO()
            Source.AnilistShow -> TODO()
            Source.CustomMovie -> TODO()
            Source.CustomShow -> TODO()
        }
    }
}