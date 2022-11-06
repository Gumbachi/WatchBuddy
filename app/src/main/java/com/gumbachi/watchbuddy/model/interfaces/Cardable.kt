package com.gumbachi.watchbuddy.model.interfaces

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.gumbachi.watchbuddy.model.enums.ScoreFormat

interface Cardable {

    @Composable
    fun Card(
        scoreFormat: ScoreFormat,
        modifier: Modifier,
    )

    @Composable
    fun CompactCard(
        scoreFormat: ScoreFormat,
        modifier: Modifier
    )
}