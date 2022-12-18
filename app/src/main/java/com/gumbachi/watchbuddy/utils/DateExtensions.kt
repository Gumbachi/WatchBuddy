package com.gumbachi.watchbuddy.utils

import com.gumbachi.watchbuddy.model.enums.data.ReleaseStatus
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn

fun LocalDate?.toMovieReleaseStatus(): ReleaseStatus = this?.let {
    val today = Clock.System.todayIn(TimeZone.currentSystemDefault())
    return if (it < today) ReleaseStatus.Released() else ReleaseStatus.Unreleased()
} ?: ReleaseStatus.Unknown()


//fun LocalDateTime.formattedTimeFromNow(): String {
//    val now = LocalDateTime.now()
//
//    val minutesElapsed = ChronoUnit.MINUTES.between(this, now)
//    val hoursElapsed = ChronoUnit.HOURS.between(this, now)
//    val daysElapsed = ChronoUnit.DAYS.between(this, now)
//
//    if (daysElapsed != 0L) return "${daysElapsed}d ago"
//    if (hoursElapsed != 0L) return "${hoursElapsed}hr ago"
//    return "${minutesElapsed}m ago"
//}
