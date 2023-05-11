package com.gumbachi.watchbuddy.datasource.anilist.mappers

import com.gumbachi.watchbuddy.AnimeDetailsQuery
import com.gumbachi.watchbuddy.datasource.anilist.model.AnilistAnimeDetails
import com.gumbachi.watchbuddy.model.enums.configuration.AnilistTitleLanguage
import com.gumbachi.watchbuddy.model.enums.data.MediaType
import com.gumbachi.watchbuddy.type.MediaFormat
import kotlinx.datetime.LocalDate


fun AnimeDetailsQuery.Data.toAnilistAnimeDetails(): AnilistAnimeDetails = Media!!.let { media ->
    val mediaType = media.format.toMediaType()
    val startDate = media.startDate
    val endDate = media.endDate

    AnilistAnimeDetails(
        id = media.id,
        posterURL = media.coverImage?.extraLarge ?: "",
        backdropURL = media.bannerImage ?: "",
        titles = mapOf(
            AnilistTitleLanguage.English to media.title?.english,
            AnilistTitleLanguage.Romaji to media.title?.romaji,
            AnilistTitleLanguage.Native to media.title?.native
        ),
        description = media.description,
        mediaType = mediaType,
        status = media.status.toReleaseStatus(mediaType),
        totalEpisodes = media.episodes,
        duration = media.duration,
        averageScore = media.averageScore,
        genres = media.genres?.filterNotNull() ?: emptyList(),
        popularity = media.popularity,
        adult = media.isAdult,
        source = media.source?.name,
        studios = media.studios?.nodes?.mapNotNull { it?.name } ?: emptyList(),
        startDate = LocalDate.of(startDate?.year, startDate?.month, startDate?.day),
        endDate = LocalDate.of(endDate?.year, endDate?.month, endDate?.day),
        animeSeason = media.season?.toString(),
        animeSeasonYear = media.seasonYear,
        recommendations = media.recommendations?.nodes?.mapNotNull {
            try {
                val recommendation = it!!.mediaRecommendation!!
                AnilistAnimeDetails.ConnectedMedia(
                    id = recommendation.id,
                    titles = mapOf(
                        AnilistTitleLanguage.English to recommendation.title?.english,
                        AnilistTitleLanguage.Romaji to recommendation.title?.romaji,
                        AnilistTitleLanguage.Native to recommendation.title?.native
                    ),
                    posterURL = recommendation.coverImage?.large ?: "",
                    averageScore = recommendation.averageScore,
                    mediaType = recommendation.format.toMediaType()
                )
            } catch (_: Exception) {
                null
            }
        } ?: emptyList(),
        relations = media.relations?.nodes?.mapNotNull {
            try {
                AnilistAnimeDetails.ConnectedMedia(
                    id = it!!.id,
                    titles = mapOf(
                        AnilistTitleLanguage.English to it.title?.english,
                        AnilistTitleLanguage.Romaji to it.title?.romaji,
                        AnilistTitleLanguage.Native to it.title?.native
                    ),
                    posterURL = it.coverImage?.large ?: "",
                    averageScore = it.averageScore,
                    mediaType = it.format.toMediaType()
                )
            } catch (_: Exception) {
                null
            }
        } ?: emptyList(),
        reviews = media.reviews?.nodes?.mapNotNull { node ->
            try {
                val it = node!!
                AnilistAnimeDetails.UserReview(
                    content = it.body!!,
                    rating = it.score,
                    author = it.user?.name!!,
                    avatarURL = it.user.avatar?.medium ?: "",
                )
            } catch (_: NullPointerException) {
                null
            }
        } ?: emptyList(),
        characters = media.characters?.edges?.mapNotNull { edge ->
            try {
                val it = edge!!
                AnilistAnimeDetails.Character(
                    name = it.node!!.name!!.full!!,
                    posterURL = it.node.image!!.large!!,
                    voiceActor = it.voiceActors?.first()?.name?.full!!
                )
            } catch (_: Exception) {
                null
            }
        } ?: emptyList(),
        staff = media.staff?.nodes?.mapNotNull { node ->
            try {
                val it = node!!
                AnilistAnimeDetails.Staff(
                    name = it.name?.full!!,
                    profileURL = it.image?.large ?: "",
                    occupations = it.primaryOccupations?.filterNotNull() ?: emptyList()
                )
            } catch (_: NullPointerException) {
                null
            }
        } ?: emptyList()
    )
}


