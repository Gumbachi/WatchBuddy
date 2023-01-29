package com.gumbachi.watchbuddy.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gumbachi.watchbuddy.ui.theme.WatchBuddyTheme
import com.gumbachi.watchbuddy.utils.surfaceColorAtElevation

@Composable
fun ColorWrappedColumn(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(12.dp),
    innerPadding: PaddingValues = PaddingValues(4.dp),
    color: Color = MaterialTheme.colorScheme.surfaceColorAtElevation(1),
    verticalArrangement: Arrangement.Vertical = Arrangement.spacedBy(0.dp),
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(
        modifier = modifier
            .clip(shape)
            .background(color)
            .padding(innerPadding),
        verticalArrangement = verticalArrangement,
        horizontalAlignment = horizontalAlignment
    ) {
        content()
    }
}

@Preview
@Composable
private fun PreviewDark() {
    WatchBuddyTheme(darkTheme = true) {
        Surface {
            ColorWrappedColumn {
                Text(text = "howdy", style = MaterialTheme.typography.displayMedium)
            }
        }
    }
}