package com.gumbachi.watchbuddy.data.local.realm.objects

import com.gumbachi.watchbuddy.data.local.realm.mappers.toAnilistMovie
import com.gumbachi.watchbuddy.data.local.realm.mappers.toCustomMovie
import com.gumbachi.watchbuddy.data.local.realm.mappers.toTMDBMovie
import com.gumbachi.watchbuddy.model.enums.data.API
import com.gumbachi.watchbuddy.model.interfaces.Movie
import com.gumbachi.watchbuddy.model.interfaces.WatchbuddyRealmObject
import com.gumbachi.watchbuddy.model.toWatchbuddyID
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

class RealmMovie() : RealmObject, WatchbuddyRealmObject {

    @PrimaryKey
    var id = ""

    var title = ""
    var posterURL = ""
    var releaseDate: Int? = null
    var runtime = ""

    var watchStatus = ""
    var userScore = 0
    var userNotes = ""

    var startDate: Int? = null
    var finishDate: Int? = null
    var lastUpdate: Long? = null

    var releaseStatus: String = ""


    override fun toWatchbuddyObject(): Movie {
        val watchbuddyID = id.toWatchbuddyID()
        return when (watchbuddyID.api) {
            API.TMDB -> toTMDBMovie()
            API.Anilist -> toAnilistMovie()
            API.Custom -> toCustomMovie()
        }
    }

}