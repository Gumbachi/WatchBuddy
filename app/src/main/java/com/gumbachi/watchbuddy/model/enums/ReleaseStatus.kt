package com.gumbachi.watchbuddy.model.enums

import androidx.compose.ui.graphics.Color
import java.time.LocalDate

sealed class ReleaseStatus(val text: String, val color: Color) {
    data class Unreleased(
        val t: String = "Unreleased",
        val c: Color = Color(0xFFFF7518)
    ): ReleaseStatus(t, c)
    data class Releasing(
        val t: String = "Releasing",
        val c: Color = Color(0xFF3248A8)
    ): ReleaseStatus(t, c)
    data class Released(
        val t: String = "Released",
        val c: Color = Color(0xFF298C43)
    ): ReleaseStatus(t, c)

    companion object {
        fun fromDate(date: LocalDate): ReleaseStatus {
            return when {
                date < LocalDate.now() -> Released()
                else -> Unreleased()
            }
        }

        fun random(): ReleaseStatus {
            return listOf(
                ReleaseStatus.Unreleased(),
                ReleaseStatus.Releasing(),
                ReleaseStatus.Released()
            ).random()
        }
    }

}