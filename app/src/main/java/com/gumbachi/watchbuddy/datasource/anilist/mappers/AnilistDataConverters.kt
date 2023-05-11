package com.gumbachi.watchbuddy.datasource.anilist.mappers

import com.gumbachi.watchbuddy.AnimeDetailsQuery
import com.gumbachi.watchbuddy.AnimeEssentialsQuery
import com.gumbachi.watchbuddy.model.enums.configuration.AnilistTitleLanguage
import com.gumbachi.watchbuddy.model.enums.data.MediaType
import com.gumbachi.watchbuddy.model.enums.data.ReleaseStatus
import com.gumbachi.watchbuddy.type.MediaFormat
import com.gumbachi.watchbuddy.type.MediaStatus
import kotlinx.datetime.LocalDate
import kotlin.math.roundToInt

private const val TAG = "AnilistConverters"

//@formatter:off
fun Map<AnilistTitleLanguage, String?>.getPreferred(language: AnilistTitleLanguage) =
    getOrDefault(language, null) ?:
    getOrDefault(AnilistTitleLanguage.English, null) ?:
    getOrDefault(AnilistTitleLanguage.Romaji, null) ?:
    getOrDefault(AnilistTitleLanguage.Native, null) ?:
    "No Title Provided"
//@formatter:on



fun MediaStatus?.toReleaseStatus(type: MediaType): ReleaseStatus = when (this) {
    MediaStatus.FINISHED -> {
        if (type == MediaType.Show)
            ReleaseStatus.Finished
        else
            ReleaseStatus.Released
    }

    MediaStatus.RELEASING -> ReleaseStatus.Releasing
    MediaStatus.NOT_YET_RELEASED -> ReleaseStatus.Unreleased
    MediaStatus.CANCELLED -> ReleaseStatus.Cancelled
    else -> ReleaseStatus.Unknown
}


fun MediaFormat?.toMediaType(): MediaType = when (this) {
    MediaFormat.TV,
    MediaFormat.TV_SHORT,
    MediaFormat.ONA,
    MediaFormat.OVA,
    MediaFormat.SPECIAL -> MediaType.Show

    MediaFormat.MOVIE -> MediaType.Movie
    else -> throw Exception("Format is not a Movie or Show")
}


fun AnimeDetailsQuery.Title.getPreferred(preference: AnilistTitleLanguage? = null): String {
    if (preference == null) return english ?: romaji ?: native ?: "No Title Provided"
    return when (preference) {
        AnilistTitleLanguage.English -> english ?: romaji ?: native ?: "No Title Provided"
        AnilistTitleLanguage.Romaji -> romaji ?: english ?: native ?: "No Title Provided"
        AnilistTitleLanguage.Native -> native ?: romaji ?: english ?: "No Title Provided"
    }
}

fun getPreferredTitleLanguage(
    english: String?,
    romaji: String?,
    native: String?,
    preference: AnilistTitleLanguage = AnilistTitleLanguage.English
) = when (preference) {
    AnilistTitleLanguage.English -> english ?: romaji ?: native ?: "No Title Provided"
    AnilistTitleLanguage.Romaji -> romaji ?: english ?: native ?: "No Title Provided"
    AnilistTitleLanguage.Native -> native ?: romaji ?: english ?: "No Title Provided"
}


fun Int?.toRuntimeString(unknownValue: String = "??h??m"): String {
    if (this == null) return unknownValue
    val hours = this / 60
    val minutes = this % 60
    if (hours < 1) return "${this}m"
    return "${hours}h${minutes}m"
}

fun Int.toRuntimeString(): String {
    val hours = this / 60
    val minutes = this % 60
    if (hours < 1) return "${this}m"
    return "${hours}h${minutes}m"
}

fun Int?.parseTimeUntilString(): String {
    if (this == null) return "???"
    val days = (this / 86400.0).roundToInt()
    if (days >= 1) return "$days days"
    val hours = (this / 3600.0).roundToInt()
    if (hours >= 1) return "$hours hours"
    return "${this / 60} minutes"
}

fun createLocalDateOrNull(year: Int?, month: Int?, day: Int?) = try {
    LocalDate(year!!, month!!, day!!)
} catch (_: NullPointerException) {
    null
}

fun LocalDate.Companion.of(year: Int?, month: Int?, day: Int?): LocalDate? = try {
    LocalDate(year!!, month!!, day!!)
} catch (_: NullPointerException) {
    null
}


fun AnimeEssentialsQuery.StartDate.toDateOrNull() = try {
    LocalDate(year!!, month!!, day!!)
} catch (_: NullPointerException) {
    null
}


fun AnimeEssentialsQuery.EndDate.toDateOrNull() = try {
    LocalDate(year!!, month!!, day!!)
} catch (_: NullPointerException) {
    null
}