package com.gumbachi.watchbuddy.ui.placeholders

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.gumbachi.watchbuddy.ui.theme.WatchBuddyTheme

@Composable
fun NothingHerePlaceholder(
    modifier: Modifier = Modifier,
    text: String = "Nothing to see here",
    iconSize: Dp = 150.dp,
    textStyle: TextStyle = MaterialTheme.typography.displaySmall
) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                imageVector = Icons.Filled.Close,
                contentDescription = "No Media to Display",
                modifier = Modifier.size(iconSize),
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = text,
                style = textStyle,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
        }
    }
    
}

@Preview
@Composable
private fun PreviewDark(dark: Boolean = true) {
    WatchBuddyTheme(darkTheme = dark) {
        Surface {
            NothingHerePlaceholder(Modifier.fillMaxSize())
        }
    }
}

@Preview
@Composable
private fun PreviewLight() {
    PreviewDark(dark = false)
}