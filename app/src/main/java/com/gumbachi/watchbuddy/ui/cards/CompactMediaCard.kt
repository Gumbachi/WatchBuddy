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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gumbachi.watchbuddy.model.enums.data.API
import com.gumbachi.watchbuddy.ui.theme.WatchBuddyTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CompactMediaCard(
    imageURL: String,
    headline: String,
    primarySubtitle: String,
    secondarySubtitle: String,
    statusColor: Color,
    modifier: Modifier = Modifier,
    isSaved: Boolean? = null,
    api: API? = null,
    score: String? = null,
    progress: String? = null,
    onClick: () -> Unit = {},
    onLongClick: () -> Unit = {},
    onProgressClick: (() -> Unit)? = null,
) {

    val interactionSource = remember { MutableInteractionSource() }

    Card(
        shape = RoundedCornerShape(10.dp),
        modifier = modifier
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .height(105.dp)
            .combinedClickable(
                onClick = onClick,
                onLongClick = onLongClick
            )
    ) {

        Row(
            modifier = Modifier.fillMaxSize()
        ) {
            PosterImage(
                posterURL = imageURL,
                matchConstraintByHeight = true
            )
            Spacer(
                modifier = Modifier
                    .width(3.dp)
                    .fillMaxHeight()
                    .background(statusColor)
            )
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp))
                    .padding(start = 5.dp, top = 5.dp, bottom = 5.dp)
                    .fillMaxHeight()
            ) {

                // Title
                Text(
                    text = headline,
                    maxLines = 2,
                    style = MaterialTheme.typography.labelMedium,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(end = 8.dp)
                )

                Column(
                    verticalArrangement = Arrangement.spacedBy(1.dp)
                ) {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
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
                        ApiSavedIndicator(
                            api = api,
                            isSaved = isSaved
                        )
                    }


                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .offset(x = (-5).dp)
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(topEndPercent = 50, bottomEndPercent = 50))
                            .background(MaterialTheme.colorScheme.primaryContainer)
                            .padding(end = 13.dp, top = 4.dp, bottom = 4.dp)
                            .offset(x = 5.dp)

                    ) {

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            score?.let { score ->
                                Icon(
                                    imageVector = Icons.Filled.Star,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(13.dp)
                                        .padding(1.dp)
                                )
                                Text(
                                    text = score,
                                    style = MaterialTheme.typography.labelSmall,
                                )
                            }
                        }

                        progress?.let {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.clickable(
                                    interactionSource = interactionSource,
                                    indication = null
                                ) {
                                    if (onProgressClick != null) onProgressClick()
                                }
                            ) {
                                Text(
                                    text = it,
                                    style = MaterialTheme.typography.labelSmall,
                                )
                                if (onProgressClick != null) {
                                    Icon(
                                        imageVector = Icons.Default.Add,
                                        contentDescription = "Increment Progress",
                                        modifier = Modifier.size(13.dp)
                                    )
                                }
                            }
                        }
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

    if (api == null && isSaved != true) return

    Row(
        horizontalArrangement = Arrangement.spacedBy(2.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(horizontal = 8.dp, vertical = 1.dp)
    ) {
        api?.let { api ->
            Icon(
                painter = painterResource(id = api.logo),
                contentDescription = api.name,
                tint = MaterialTheme.colorScheme.onPrimaryContainer,
                modifier = Modifier.size(20.dp)
            )
        }

        if (isSaved != null && isSaved) {
            Icon(
                imageVector = Icons.Filled.Check,
                contentDescription = "Saved",
                tint = MaterialTheme.colorScheme.onPrimaryContainer,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Preview
@Composable
private fun DefaultCompactMediaCardPreview(darkMode: Boolean = false) {
    WatchBuddyTheme(darkTheme = darkMode) {
        Surface(modifier = Modifier.padding(16.dp)) {
            CompactMediaCard(
                imageURL = "",
                headline = "Movie or Show TitleMovie or Show TitleMovie or Show TitleMovie or Show TitleMovie or Show TitleMovie or Show Title",
                primarySubtitle = "1h 24m",
                secondarySubtitle = "2021-05-27",
                score = "10",
                statusColor = Color.Blue,
                progress = "50 / 128",
                modifier = Modifier,
                api = API.TMDB,
                isSaved = true,
                onProgressClick = {}
            )
        }
    }
}

@Preview
@Composable
private fun CompactMediaCardPreview() {
    DefaultCompactMediaCardPreview(darkMode = true)
}
