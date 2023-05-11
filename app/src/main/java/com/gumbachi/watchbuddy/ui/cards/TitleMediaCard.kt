package com.gumbachi.watchbuddy.ui.cards

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gumbachi.watchbuddy.ui.components.FixedText
import com.gumbachi.watchbuddy.ui.theme.WatchBuddyTheme
import com.gumbachi.watchbuddy.utils.LONG_MEDIA_TITLE

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TitleMediaCard(
    posterURL: String,
    title: String,
    modifier: Modifier = Modifier,
    color: Color = Color.Transparent,
    onClick: () -> Unit = {},
    lines: Int = 2
) {
    Card(
        onClick = onClick,
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = color
        )
    ) {
        PosterImage(posterURL = posterURL)
        Box(modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)) {
            FixedText(
                text = title,
                maxLines = lines,
                minLines = lines,
                style = MaterialTheme.typography.labelMedium
            )
        }
    }
}

@Preview
@Composable
private fun PreviewDark() {
    WatchBuddyTheme(darkTheme = true) {
        Surface {
            TitleMediaCard(posterURL = "", title = LONG_MEDIA_TITLE)
        }
    }
}

@Preview
@Composable
private fun PreviewLight() {
    WatchBuddyTheme(darkTheme = false) {
        Surface {
            TitleMediaCard(posterURL = "", title = "Movie/Show Title")
        }
    }
}