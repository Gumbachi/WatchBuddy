package com.gumbachi.watchbuddy.model.enums

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
}