package com.gumbachi.watchbuddy.ui.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gumbachi.watchbuddy.model.enums.CardStyle
import com.gumbachi.watchbuddy.model.enums.ScoreFormat
import com.gumbachi.watchbuddy.model.enums.format
import com.gumbachi.watchbuddy.ui.app.components.CompactMediaCard
import com.gumbachi.watchbuddy.ui.app.components.MediaCard
import com.gumbachi.watchbuddy.ui.theme.getElevation

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel = viewModel()
) {

    val state = viewModel.state

    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(state = scrollState)
            .padding(horizontal = 8.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {

        Text(
            text = "Card Style",
            style = MaterialTheme.typography.titleLarge
        )

        CardCustomizer(
            cardStyle = state.cardStyle,
            onCardStyleChange = { viewModel.onCardStyleUpdate(it) },
            scoreFormat = state.scoreFormat,
            onScoreFormatChange = { viewModel.onScoreFormatUpdate(it) }
        )
    }
}

@Composable
private fun CardCustomizer(
    modifier: Modifier = Modifier,
    cardStyle: CardStyle = CardStyle.Normal,
    onCardStyleChange: (CardStyle) -> Unit = {},
    scoreFormat: ScoreFormat = ScoreFormat.Decimal,
    onScoreFormatChange: (ScoreFormat) -> Unit = {}
) {
    
    var scoreFormatExpanded by remember { mutableStateOf(false) }
    var cardStyleExpanded by remember { mutableStateOf(false) }

    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = { cardStyleExpanded = true }) {
                Text(text = "Card: $cardStyle")
                Icon(imageVector = Icons.Filled.ArrowDropDown, contentDescription = null)
                DropdownMenu(
                    expanded = cardStyleExpanded,
                    onDismissRequest = { cardStyleExpanded = false }
                ) {
                    CardStyle.values().forEach {
                        DropdownMenuItem(
                            text = { Text(text = it.toString()) },
                            onClick = {
                                onCardStyleChange(it)
                                cardStyleExpanded = false
                            }
                        )
                    }
                }
            }
            Button(onClick = { scoreFormatExpanded = true }) {
                Text(text = "Score: $scoreFormat")
                Icon(imageVector = Icons.Filled.ArrowDropDown, contentDescription = null)
                DropdownMenu(
                    expanded = scoreFormatExpanded,
                    onDismissRequest = { scoreFormatExpanded = false }
                ) {
                    ScoreFormat.values().forEach {
                        DropdownMenuItem(
                            text = { Text(text = it.toString()) },
                            onClick = {
                                onScoreFormatChange(it)
                                scoreFormatExpanded = false
                            }
                        )
                    }
                }
            }
        }
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(MaterialTheme.colorScheme.surfaceColorAtElevation(getElevation(1)))
                .padding(vertical = 16.dp)
        ) {
            when (cardStyle) {
                CardStyle.Normal -> {
                    MediaCard(
                        imageURL = "",
                        headline = "Media Title",
                        primarySubtitle = "Label 1",
                        secondarySubtitle = "Label 2",
                        score = 8.3.format(scoreFormat),
                        statusText = "Status",
                        statusColor = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.fillMaxWidth(0.5F)
                    )
                }
                CardStyle.Compact -> {
                    CompactMediaCard(
                        imageURL = "",
                        headline = "Media Title",
                        primarySubtitle = "Label 1",
                        secondarySubtitle = "Label 2",
                        score = 8.3.format(scoreFormat),
                        statusColor = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.fillMaxWidth(0.9F)
                    )
                }
            }
        }
    }
}


@Composable
private fun LabeledSwitch(
    label: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null
) {

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            icon?.let { Icon(imageVector = icon, contentDescription = null) }
            Text(text = label)
        }
        Switch(
            checked = checked,
            onCheckedChange = { onCheckedChange(it) }
        )
    }
}