package com.gumbachi.watchbuddy.ui.settings.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.gumbachi.watchbuddy.model.WatchBuddyID
import com.gumbachi.watchbuddy.model.enums.configuration.CardStyle
import com.gumbachi.watchbuddy.model.enums.configuration.ScoreFormat
import com.gumbachi.watchbuddy.model.enums.data.API
import com.gumbachi.watchbuddy.model.enums.data.MediaType
import com.gumbachi.watchbuddy.model.enums.data.ReleaseStatus
import com.gumbachi.watchbuddy.model.interfaces.Cardable
import com.gumbachi.watchbuddy.ui.cards.MediaCard
import com.gumbachi.watchbuddy.ui.components.ColorWrappedColumn

@OptIn(ExperimentalAnimationApi::class)
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

    ColorWrappedColumn(modifier = modifier) {
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

            AnimatedContent(targetState = cardStyle) {
                MediaCard(
                    cardData = object : Cardable {
                        override val title = "Media Title"
                        override val watchbuddyID = WatchBuddyID(API.Anilist, MediaType.Show, -1)
                        override val primaryDetail = "Label 1"
                        override val secondaryDetail = "Label 2"
                        override val posterURL = ""
                        override val score = 83
                        override val releaseStatus = ReleaseStatus.Releasing
                        override val progress = "8 / 24"
                    },
                    cardStyle = it,
                    scoreFormat = scoreFormat,
                    onClick = {},
                    onLongClick = {},
                    isSaved = true,
                    showProgress = true,
                    showApi = showApiIndicator,
                    modifier = if (it == CardStyle.Normal) {
                        Modifier.size(200.dp, 350.dp)
                    } else {
                        Modifier
                    }
                )
            }
        }
    }
}
