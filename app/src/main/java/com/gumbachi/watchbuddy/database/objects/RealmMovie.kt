package com.gumbachi.watchbuddy.database.objects

import com.gumbachi.watchbuddy.database.mappers.toAnilistMovie
import com.gumbachi.watchbuddy.database.mappers.toCustomMovie
import com.gumbachi.watchbuddy.database.mappers.toTMDBMovie
import com.gumbachi.watchbuddy.model.enums.data.API
import com.gumbachi.watchbuddy.model.interfaces.Movie
import com.gumbachi.watchbuddy.model.toWatchbuddyID
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

class RealmMovie(
    @PrimaryKey var id: String,
    var title: String = "",
    var posterURL: String = "",
    var releaseDate: Int? = null,
    var runtime: String = "",
    var watchStatus: String = "",
    var userScore: Int = 0,
    var userNotes: String = "",
    var startDate: Int? = null,
    var finishDate: Int? = null,
    var lastUpdate: Long? = null,
    var releaseStatus: String = ""
) : RealmObject {

    // Empty Constructor Required for Realm Objects
    constructor(): this(id = "")

    fun toMovie(): Movie {
        val watchbuddyID = id.toWatchbuddyID()
        return when (watchbuddyID.api) {
            API.TMDB -> toTMDBMovie()
            API.Anilist -> toAnilistMovie()
            API.Custom -> toCustomMovie()
        }
    }
}