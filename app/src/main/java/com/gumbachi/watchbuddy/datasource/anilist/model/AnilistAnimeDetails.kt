package com.gumbachi.watchbuddy.datasource.anilist.model

import com.gumbachi.watchbuddy.datasource.anilist.mappers.toRuntimeString
import com.gumbachi.watchbuddy.model.WatchBuddyID
import com.gumbachi.watchbuddy.model.enums.configuration.AnilistTitleLanguage
import com.gumbachi.watchbuddy.model.enums.configuration.ScoreFormat
import com.gumbachi.watchbuddy.model.enums.data.API
import com.gumbachi.watchbuddy.model.enums.data.MediaType
import com.gumbachi.watchbuddy.model.enums.data.ReleaseStatus
import com.gumbachi.watchbuddy.model.interfaces.MediaDetails
import com.gumbachi.watchbuddy.model.interfaces.Review
import com.gumbachi.watchbuddy.utils.toString
import kotlinx.datetime.LocalDate

data class AnilistAnimeDetails(
    override val id: Int,
    override val posterURL: String,
    val backdropURL: String,
    val titles: Map<AnilistTitleLanguage, String?>,
    val description: String?,
    val mediaType: MediaType,
    val status: ReleaseStatus,
    val totalEpisodes: Int?,
    private val duration: Int?,
    private val averageScore: Int?,
    val genres: List<String>,
    val popularity: Int?,
    val adult: Boolean?,
    val source: String?,
    val studios: List<String>,
    val startDate: LocalDate?,
    val endDate: LocalDate?,
    private val animeSeason: String?,
    private val animeSeasonYear: Int?,

    val recommendations: List<ConnectedMedia>,
    val relations: List<ConnectedMedia>,

    val reviews: List<UserReview>,

    val characters: List<Character>,
    val staff: List<Staff>

) : MediaDetails {

    override val title = titles.firstNotNullOf { it.value }
    override val watchBuddyID = WatchBuddyID(API.Anilist, mediaType, id)


    fun getAverageScore(format: ScoreFormat, decorated: Boolean = true): String? =
        averageScore?.toString(format = format, decorated = decorated)

    fun getFormattedDuration() = duration?.toRuntimeString()


    // Internal Classes
    data class ConnectedMedia(
        val id: Int,
        val titles: Map<AnilistTitleLanguage, String?>,
        val posterURL: String,
        val averageScore: Int?,
        val mediaType: MediaType
    )

    data class UserReview(
        override val content: String,
        override val rating: Int?,
        override val author: String,
        override val avatarURL: String,
    ): Review

    data class Character(
        val name: String,
        val posterURL: String,
        val voiceActor: String
    )

    data class Staff(
        val name: String,
        val profileURL: String,
        val occupations: List<String>
    )
}
