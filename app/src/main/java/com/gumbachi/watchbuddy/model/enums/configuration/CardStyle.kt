package com.gumbachi.watchbuddy.model.enums.configuration

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

enum class CardStyle(val requiredWidth: Dp) {
    Normal(160.dp),
    Compact(300.dp)
}