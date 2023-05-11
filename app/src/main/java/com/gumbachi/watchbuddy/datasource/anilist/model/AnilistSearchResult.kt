package com.gumbachi.watchbuddy.datasource.anilist.model

import com.gumbachi.watchbuddy.datasource.anilist.mappers.parseTimeUntilString
import com.gumbachi.watchbuddy.model.WatchBuddyID
import com.gumbachi.watchbuddy.model.enums.data.API
import com.gumbachi.watchbuddy.model.enums.data.MediaType
import com.gumbachi.watchbuddy.model.enums.data.ReleaseStatus
import com.gumbachi.watchbuddy.model.interfaces.SearchResult
import kotlinx.datetime.LocalDate

data class AnilistSearchResult(
    override val id: Int,
    override val title: String,
    override val posterURL: String,
    override val averageScore: Int,
    val popularity: Int,
    val type: MediaType,
    override val releaseStatus: ReleaseStatus,

    val timeUntilNextEpisode: Int? = null,
    val nextEpisode: Int? = null,
    val releaseDate: LocalDate? = null,
    val totalEpisodes: Int? = null,

): SearchResult {

    override val watchbuddyID = WatchBuddyID(API.Anilist, type, id)

    override val primaryDetail = releaseDate?.let { "$type \u2022 ${it.year}" } ?: "$type"
    override val secondaryDetail = when (releaseStatus) {
        ReleaseStatus.Releasing -> "EP $nextEpisode in ${timeUntilNextEpisode.parseTimeUntilString()}"
        else -> releaseStatus.toString()
    }

    override fun weight() = popularity.toDouble()

    override val progress = totalEpisodes?.let { "$it Episodes" } ?: "?? Episodes"
    override val score: Int
        get() = averageScore

}
