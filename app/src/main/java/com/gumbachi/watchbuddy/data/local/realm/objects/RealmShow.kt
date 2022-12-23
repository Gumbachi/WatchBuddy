package com.gumbachi.watchbuddy.data.local.realm.objects

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

class RealmShow() : RealmObject {

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
    var totalEpisodes = 0


    var startDate: Int? = null
    var finishDate: Int? = null
    var lastUpdate: Long? = null

    var releaseStatus: String = ""

}