package com.gumbachi.watchbuddy.model.enums.data

import androidx.compose.ui.graphics.Color

enum class ReleaseStatus(val color: Color) {
    Unreleased(Color(0xFFFF7518)),
    Releasing(Color(0xFF3248A8)),
    Released(Color(0xFF298C43)),
    Cancelled(Color(0xFFFF0000)),
    Unknown(Color(0xFF808080)),
    Finished(Color(0xFF298C43)),
}