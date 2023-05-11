package com.gumbachi.watchbuddy.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.gumbachi.watchbuddy.ui.theme.WatchBuddyTheme

@Composable
fun FixedText(
    text: String,
    modifier: Modifier = Modifier,
    minLines: Int = 1,
    maxLines: Int = Int.MAX_VALUE,
    style: TextStyle = MaterialTheme.typography.bodyMedium,
    color: Color = MaterialTheme.colorScheme.onSurface,
    overflow: TextOverflow = TextOverflow.Ellipsis
) {

    var lines by remember { mutableStateOf(0) }

    Column {
        Text(
            text = text,
            style = style,
            color = color,
            maxLines = maxLines,
            overflow = overflow,
            onTextLayout = { layoutResult ->
                lines = layoutResult.lineCount
            },
            modifier = modifier
        )
        (lines until minLines).forEach { _ ->
            Text(text = "", style = style)
        }
    }


}

@Preview
@Composable
private fun PreviewDark(dark: Boolean = true) {
    WatchBuddyTheme(darkTheme = dark) {
        Surface {
            FixedText(text = "Howdy", maxLines = 3, minLines = 3)
        }
    }
}

@Preview
@Composable
private fun PreviewLight() {
    PreviewDark(dark = false)
}