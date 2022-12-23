package com.gumbachi.watchbuddy.utils

import com.gumbachi.watchbuddy.model.enums.data.ReleaseStatus
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn

fun LocalDate?.toMovieReleaseStatus(): ReleaseStatus = this?.let {
    val today = Clock.System.todayIn(TimeZone.currentSystemDefault())
    return if (it < today) ReleaseStatus.Released else ReleaseStatus.Unreleased
} ?: ReleaseStatus.Unknown

fun LocalDate?.toShowReleaseStatus(endDate: LocalDate?): ReleaseStatus {
    if (this == null || endDate == null) return ReleaseStatus.Unknown
    val today = Clock.System.todayIn(TimeZone.currentSystemDefault())

     return when {
         this > today -> ReleaseStatus.Unreleased
         this < today && endDate < today -> ReleaseStatus.Released
         this < today && endDate > today -> ReleaseStatus.Releasing
         else -> ReleaseStatus.Unknown
     }
}
