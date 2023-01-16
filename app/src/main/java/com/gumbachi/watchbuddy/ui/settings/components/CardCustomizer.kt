package com.gumbachi.watchbuddy.ui.settings.components

import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.gumbachi.watchbuddy.model.enums.configuration.CardStyle
import com.gumbachi.watchbuddy.model.enums.configuration.ScoreFormat
import com.gumbachi.watchbuddy.model.enums.data.API
import com.gumbachi.watchbuddy.ui.cards.CompactMediaCard
import com.gumbachi.watchbuddy.ui.cards.NormalMediaCard
import com.gumbachi.watchbuddy.ui.components.ColoredCardWrapper

@Composable
fun CardCustomizer(
    modifier: Modifier = Modifier,
    cardStyle: CardStyle = CardStyle.Normal,
    onCardStyleChange: (CardStyle) -> Unit = {},
    scoreFormat: ScoreFormat = ScoreFormat.Decimal,
    onScoreFormatChange: (ScoreFormat) -> Unit = {},
    showApiIndicator: Boolean = true,
) {

    var scoreFormatExpanded by remember { mutableStateOf(false) }
    var cardStyleExpanded by remember { mutableStateOf(false) }

    val transition = updateTransition(targetState = cardStyle, label = "")

    val cardWidth by transition.animateDp(
        label = "",
        transitionSpec = { tween(800) }
    ) {
        when (it) {
            CardStyle.Normal -> {
                200.dp
            }

            CardStyle.Compact -> {
                400.dp
            }
        }
    }

    val cardHeight by transition.animateDp(
        label = "",
        transitionSpec = { tween(800) }
    ) {
        when (it) {
            CardStyle.Normal -> {
                350.dp
            }

            CardStyle.Compact -> {
                105.dp
            }
        }
    }

    ColoredCardWrapper {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
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

            when (cardStyle) {
                CardStyle.Normal -> {
                    NormalMediaCard(
                        imageURL = "",
                        headline = "Media Title",
                        primarySubtitle = "Label 1",
                        secondarySubtitle = "Label 2",
                        score = scoreFormat.example,
                        statusText = "Status",
                        statusColor = MaterialTheme.colorScheme.primary,
                        progress = "8 / 24",
                        api = if (showApiIndicator) API.Anilist else null,
                        isSaved = true,
                        modifier = Modifier.size(cardWidth, cardHeight)
                    )
                }

                CardStyle.Compact -> {
                    CompactMediaCard(
                        imageURL = "",
                        headline = "Media Title",
                        primarySubtitle = "Label 1",
                        secondarySubtitle = "Label 2",
                        score = scoreFormat.example,
                        statusColor = MaterialTheme.colorScheme.primary,
                        api = if (showApiIndicator) API.TMDB else null,
                        isSaved = true,
                        progress = "8 / 24",
                        modifier = Modifier.size(cardWidth, cardHeight)
                    )
                }
            }
        }
    }

//    Box(
//        contentAlignment = Alignment.Center,
//        modifier = modifier
//            .fillMaxWidth()
//            .clip(RoundedCornerShape(16.dp))
//            .background(MaterialTheme.colorScheme.surfaceColorAtElevation(1))
//            .padding(16.dp)
//    ) {
//
//        Column(horizontalAlignment = Alignment.CenterHorizontally) {
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(vertical = 8.dp),
//                horizontalArrangement = Arrangement.SpaceEvenly
//            ) {
//                Button(onClick = { cardStyleExpanded = true }) {
//                    Text(text = "Card: $cardStyle")
//                    Icon(imageVector = Icons.Filled.ArrowDropDown, contentDescription = null)
//                    DropdownMenu(
//                        expanded = cardStyleExpanded,
//                        onDismissRequest = { cardStyleExpanded = false }
//                    ) {
//                        CardStyle.values().forEach {
//                            DropdownMenuItem(
//                                text = { Text(text = it.toString()) },
//                                onClick = {
//                                    onCardStyleChange(it)
//                                    cardStyleExpanded = false
//                                }
//                            )
//                        }
//                    }
//                }
//                Button(onClick = { scoreFormatExpanded = true }) {
//                    Text(text = "Score: $scoreFormat")
//                    Icon(imageVector = Icons.Filled.ArrowDropDown, contentDescription = null)
//                    DropdownMenu(
//                        expanded = scoreFormatExpanded,
//                        onDismissRequest = { scoreFormatExpanded = false }
//                    ) {
//                        ScoreFormat.values().forEach {
//                            DropdownMenuItem(
//                                text = { Text(text = it.toString()) },
//                                onClick = {
//                                    onScoreFormatChange(it)
//                                    scoreFormatExpanded = false
//                                }
//                            )
//                        }
//                    }
//                }
//            }
//
//            when (cardStyle) {
//                CardStyle.Normal -> {
//                    NormalMediaCard(
//                        imageURL = "",
//                        headline = "Media Title",
//                        primarySubtitle = "Label 1",
//                        secondarySubtitle = "Label 2",
//                        score = scoreFormat.example,
//                        statusText = "Status",
//                        statusColor = MaterialTheme.colorScheme.primary,
//                        progress = "8 / 24",
//                        api = if (showApiIndicator) API.Anilist else null,
//                        isSaved = true,
//                        modifier = Modifier.size(cardWidth, cardHeight)
//                    )
//                }
//
//                CardStyle.Compact -> {
//                    CompactMediaCard(
//                        imageURL = "",
//                        headline = "Media Title",
//                        primarySubtitle = "Label 1",
//                        secondarySubtitle = "Label 2",
//                        score = scoreFormat.example,
//                        statusColor = MaterialTheme.colorScheme.primary,
//                        api = if (showApiIndicator) API.TMDB else null,
//                        isSaved = true,
//                        progress = "8 / 24",
//                        modifier = Modifier.size(cardWidth, cardHeight)
//                    )
//                }
//            }
//        }
//    }
}
