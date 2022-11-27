package com.gumbachi.watchbuddy.utils

import com.gumbachi.watchbuddy.model.enums.data.ReleaseStatus
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

fun LocalDate?.getMovieReleaseStatus(): ReleaseStatus {
    if (this == null) return ReleaseStatus.Unknown()

    return when {
        this < LocalDate.now() -> ReleaseStatus.Released()
        else -> ReleaseStatus.Unreleased()
    }
}

fun LocalDateTime.formattedTimeFromNow(): String {
    val now = LocalDateTime.now()

    val minutesElapsed = ChronoUnit.MINUTES.between(this, now)
    val hoursElapsed = ChronoUnit.HOURS.between(this, now)
    val daysElapsed = ChronoUnit.DAYS.between(this, now)

    if (daysElapsed != 0L) return "${daysElapsed}d ago"
    if (hoursElapsed != 0L) return "${hoursElapsed}hr ago"
    return "${minutesElapsed}m ago"
}
