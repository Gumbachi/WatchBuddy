package com.gumbachi.watchbuddy.data.local.realm.objects

import com.gumbachi.watchbuddy.data.local.realm.mappers.toAnilistShow
import com.gumbachi.watchbuddy.data.local.realm.mappers.toCustomShow
import com.gumbachi.watchbuddy.data.local.realm.mappers.toTMDBShow
import com.gumbachi.watchbuddy.model.enums.data.API
import com.gumbachi.watchbuddy.model.interfaces.Show
import com.gumbachi.watchbuddy.model.interfaces.WatchbuddyRealmObject
import com.gumbachi.watchbuddy.model.toWatchbuddyID
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

class RealmShow() : RealmObject, WatchbuddyRealmObject {

    @PrimaryKey
    var id = ""

    var title = ""
    var posterURL = ""

    var releaseDate: Int? = null
    var endDate: Int? = null

    var watchStatus = ""
    var userScore = 0
    var userNotes = ""

    var episodesWatched = 0
    var totalEpisodes: Int? = null


    var startDate: Int? = null
    var finishDate: Int? = null
    var lastUpdate: Long? = null

    var releaseStatus: String = ""


    override fun toWatchbuddyObject(): Show {
        val watchbuddyID = id.toWatchbuddyID()
        return when (watchbuddyID.api) {
            API.TMDB -> toTMDBShow()
            API.Anilist -> toAnilistShow()
            API.Custom -> toCustomShow()
        }
    }

}