package com.gumbachi.watchbuddy.model.enums.data

import androidx.compose.ui.graphics.Color

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
    data class Unknown(
        val t: String = "Unknown",
        val c: Color = Color(0xFF808080)
    ): ReleaseStatus(t, c)

    companion object {

        fun random(): ReleaseStatus {
            return listOf(
                Unreleased(),
                Releasing(),
                Released(),
                Unknown()
            ).random()
        }
    }

}