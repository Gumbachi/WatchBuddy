package com.gumbachi.watchbuddy.ui.cards

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gumbachi.watchbuddy.model.enums.data.API
import com.gumbachi.watchbuddy.ui.theme.WatchBuddyTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NormalMediaCard(
    imageURL: String,
    headline: String,
    primarySubtitle: String,
    secondarySubtitle: String,
    statusText: String,
    statusColor: Color,
    modifier: Modifier = Modifier,
    api: API? = null,
    isSaved: Boolean? = false,
    score: String? = null,
    progress: String? = null,
    onClick: () -> Unit = {},
    onLongClick: () -> Unit = {},
    onProgressClick: () -> Unit = {}
) {
    Card(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .requiredSizeIn(minWidth = 150.dp, minHeight = 200.dp)
            .padding(horizontal = 4.dp, vertical = 4.dp)
            .combinedClickable(
                interactionSource = MutableInteractionSource(),
                indication = null,
                onClick = onClick,
                onLongClick = onLongClick
            )
            .wrapContentHeight()
    ) {

        Box {
            // Top Portion of Card
            Box(
                Modifier
                    .fillMaxWidth()
                    .align(Alignment.TopCenter)
            ) {
                PosterImage(
                    posterURL = imageURL,
                    aspectRatio = 0.66F,
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(bottom = 66.dp)
                )

                api?.let {
                    ApiSavedIndicator(
                        api = api,
                        isSaved = isSaved,
                        modifier = Modifier
                            .clip(RoundedCornerShape(bottomStart = 16.dp))
                            .align(Alignment.TopEnd)
                    )
                }
            }

            Column(modifier = Modifier.align(Alignment.BottomCenter)) {
                Box(modifier = Modifier.fillMaxWidth()) {
                    // Score Bubble
                    score?.let { score ->
                        CardBubble(
                            text = score,
                            leadingIcon = rememberVectorPainter(image = Icons.Filled.Star),
                            modifier = Modifier
                                .clip(RoundedCornerShape(topStart = 16.dp))
                                .align(Alignment.BottomEnd)
                        )
                    }

                    // Progress Bubble
                    progress?.let {
                        CardBubble(
                            text = it,
                            trailingIcon = when (onProgressClick != {}) {
                                true -> rememberVectorPainter(image = Icons.Filled.Add)
                                false -> null
                            },
                            modifier = Modifier
                                .clip(RoundedCornerShape(topEnd = 16.dp))
                                .align(Alignment.BottomStart)
                                .clickable(
                                    interactionSource = MutableInteractionSource(),
                                    indication = null
                                ) { onProgressClick() }
                        )
                    }
                }


                // Bottom Portion of Card
                Column(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp))
                        .padding(horizontal = 8.dp, vertical = 8.dp)
                ) {

                    // Title
                    Text(
                        text = headline,
                        style = MaterialTheme.typography.labelMedium,
                        textAlign = TextAlign.Left,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 4.dp)
                    )

                    // Subtitles/Status
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column {
                            Text(
                                text = primarySubtitle,
                                style = MaterialTheme.typography.labelSmall
                            )
                            Text(
                                text = secondarySubtitle,
                                style = MaterialTheme.typography.labelSmall
                            )
                        }
                        StatusBubble(
                            text = statusText,
                            color = statusColor
                        )
                    }
                }

            }
        }
    }
}


@Composable
private fun ApiSavedIndicator(
    api: API?,
    isSaved: Boolean?,
    modifier: Modifier = Modifier,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(2.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .background(Color.Black.copy(alpha = 0.9F))
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        api?.let {
            Icon(
                painter = painterResource(id = api.logo),
                contentDescription = api.name,
                tint = Color.White,
                modifier = Modifier.size(20.dp)
            )
        }

        if (isSaved != null && isSaved) {
            Icon(
                imageVector = Icons.Filled.Check,
                contentDescription = "Saved",
                tint = Color.White,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}


@Composable
private fun CardBubble(
    text: String,
    modifier: Modifier = Modifier,
    leadingIcon: Painter? = null,
    trailingIcon: Painter? = null,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .background(color = Color.Black.copy(alpha = 0.9f))
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(2.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            leadingIcon?.let {
                Icon(
                    painter = it,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(14.dp)
                )
            }
            Text(
                text = text,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                color = Color.White,
                modifier = Modifier
            )
            trailingIcon?.let {
                Icon(
                    painter = it,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(14.dp)
                )
            }
        }
    }
}

@Composable
private fun StatusBubble(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.primary
) {
    Text(
        text = text,
        style = MaterialTheme.typography.labelSmall,
        maxLines = 1,
        color = Color.White,
        modifier = modifier
            .clip(CircleShape)
            .background(color = color)
            .padding(horizontal = 8.dp, vertical = 3.dp)

    )
}


@Preview(showBackground = false)
@Composable
private fun MediaCardPreview(darkMode: Boolean = true) {
    WatchBuddyTheme(darkTheme = darkMode) {
        NormalMediaCard(
            imageURL = "",
            headline = "Movie or Show Title",
            primarySubtitle = "1h 24m",
            secondarySubtitle = "2021-05-27",
            score = "10",
            statusText = "Released",
            statusColor = Color.Blue,
            progress = "50 / 128",
            modifier = Modifier.size(230.dp, 400.dp),
            api = API.TMDB,
            isSaved = true
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MediaCardPreviewLight() {
    MediaCardPreview(darkMode = false)
}