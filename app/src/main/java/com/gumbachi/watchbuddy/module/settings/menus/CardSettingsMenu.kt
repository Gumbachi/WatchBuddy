package com.gumbachi.watchbuddy.module.settings.menus

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Api
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.gumbachi.watchbuddy.model.enums.configuration.CardStyle
import com.gumbachi.watchbuddy.model.enums.configuration.ScoreFormat
import com.gumbachi.watchbuddy.model.settings.CardSettings
import com.gumbachi.watchbuddy.ui.settings.components.CardCustomizer
import com.gumbachi.watchbuddy.ui.settings.components.SettingsSwitch

@Composable
fun CardSettingsMenu(
    settings: CardSettings,
    updateCardStyle: (CardStyle) -> Unit,
    updateScoreFormat: (ScoreFormat) -> Unit,
    updateShowProgressOnPlanned: (Boolean) -> Unit,
    updateShowScoreOnPlanned: (Boolean) -> Unit,
    updateShowApiIndicator: (Boolean) -> Unit,
    modifier: Modifier = Modifier,

) {
    Column(
        modifier = modifier.padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        CardCustomizer(
            cardStyle = settings.style,
            scoreFormat = settings.scoreFormat,
            onCardStyleChange = updateCardStyle,
            onScoreFormatChange = updateScoreFormat,
            showApiIndicator = settings.showApi
        )
        SettingsSwitch(
            title = "Show Progress on Planned",
            description = "When disabled progress will not show for items with 'Planning' status",
            icon = Icons.Filled.Visibility,
            checked = settings.showProgressOnPlanned,
            onCheckedChange = updateShowProgressOnPlanned
        )
        SettingsSwitch(
            title = "Show Score on Planned",
            description = "When disabled score will not show for items with 'Planning' status",
            icon = Icons.Filled.Visibility,
            checked = settings.showScoreOnPlanned,
            onCheckedChange = updateShowScoreOnPlanned
        )
        SettingsSwitch(
            title = "Show Api Indicator",
            icon = Icons.Filled.Api,
            checked = settings.showApi,
            onCheckedChange = updateShowApiIndicator
        )
    }
}