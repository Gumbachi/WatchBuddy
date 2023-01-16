package com.gumbachi.watchbuddy.database.objects

import com.gumbachi.watchbuddy.database.mappers.toAnilistShow
import com.gumbachi.watchbuddy.database.mappers.toCustomShow
import com.gumbachi.watchbuddy.database.mappers.toTMDBShow
import com.gumbachi.watchbuddy.model.enums.data.API
import com.gumbachi.watchbuddy.model.interfaces.Show
import com.gumbachi.watchbuddy.model.toWatchbuddyID
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

class RealmShow(
    @PrimaryKey var id: String,
    var title: String = "",
    var posterURL: String = "",
    var releaseDate: Int? = null,
    var endDate: Int? = null,
    var watchStatus: String = "",
    var userScore: Int = 0,
    var userNotes: String = "",
    var episodesWatched: Int = 0,
    var totalEpisodes: Int? = null,
    var startDate: Int? = null,
    var finishDate: Int? = null,
    var lastUpdate: Long? = null,
    var releaseStatus: String = ""
) : RealmObject {

    // Empty Constructor Required for Realm Objects
    constructor(): this(id = "")

    fun toShow(): Show {
        val watchbuddyID = id.toWatchbuddyID()
        return when (watchbuddyID.api) {
            API.TMDB -> toTMDBShow()
            API.Anilist -> toAnilistShow()
            API.Custom -> toCustomShow()
        }
    }

}