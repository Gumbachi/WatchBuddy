package com.gumbachi.watchbuddy.ui.theme

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

object M3DialogValues {
    val containerShape = RoundedCornerShape(28.dp)
    val containerPadding = PaddingValues(24.dp)
    val requireDialogWidth = Modifier.requiredWidthIn(280.dp, 560.dp)
    val dividerHeight = 1.dp
    val iconSize = 24.dp
}

fun Modifier.dialogContainer(): Modifier =
    clip(RoundedCornerShape(28.dp))
        .padding(24.dp)
        .requiredWidthIn(280.dp, 560.dp)