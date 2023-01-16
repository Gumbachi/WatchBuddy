package com.gumbachi.watchbuddy.utils

import com.gumbachi.watchbuddy.model.WatchBuddyID
import com.gumbachi.watchbuddy.model.enums.data.API
import com.gumbachi.watchbuddy.model.enums.data.MediaType
import com.gumbachi.watchbuddy.model.enums.data.ReleaseStatus
import com.gumbachi.watchbuddy.model.enums.data.WatchStatus
import com.gumbachi.watchbuddy.model.interfaces.Cardable
import com.gumbachi.watchbuddy.model.interfaces.Media
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate

sealed class MediaDummy: Media {
    object NormalShow: MediaDummy() {
        override val watchbuddyID = WatchBuddyID(API.TMDB, MediaType.Show, -1)
        override fun clone(): Media = throw Exception("No")
        override val title = "Movie / Show Title"
        override val id = -1
        override var episodesWatched = 8
        override val totalEpisodes = 24
        override var userScore = 83
        override var userNotes = "It's Alright"
        override var watchStatus = WatchStatus.Watching
        override var startDate: LocalDate? = LocalDate(2022, 11, 24)
        override var finishDate: LocalDate? = null
        override var lastUpdate: Instant? = null
        override val releaseStatus = ReleaseStatus.Releasing
        override val releaseDate: LocalDate? = LocalDate(2018, 7, 22)
        override val primaryDetail = "Detail 1"
        override val secondaryDetail = "Detail 2"
        override val posterURL = ""
        override val score = userScore
    }
}


sealed class CardableImpl: Cardable {
    object SettingsExample: CardableImpl() {
        override val title = "Title"
        override val watchbuddyID = WatchBuddyID(API.Custom, MediaType.Show, -1)
        override val primaryDetail = "Primary Detail"
        override val secondaryDetail = "Secondary Detail"
        override val posterURL = ""
        override val score = 83
        override val releaseStatus = ReleaseStatus.Releasing
    }
}