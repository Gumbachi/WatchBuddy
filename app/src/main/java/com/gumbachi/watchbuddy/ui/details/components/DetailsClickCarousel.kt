package com.gumbachi.watchbuddy.ui.details.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gumbachi.watchbuddy.ui.theme.WatchBuddyTheme

@Composable
fun <T> DetailsClickCarousel(
    title: String,
    items: List<T>,
    modifier: Modifier = Modifier,
    content: @Composable (T) -> Unit,
) {

    if (items.isEmpty()) {
        Text(text = "No Reviews") // TODO: Clean this up
        return
    }

    var currentIndex by remember { mutableStateOf(0) }

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
    ) {
        Text(text = title, style = MaterialTheme.typography.titleMedium)
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            FilledIconButton(
                onClick = { currentIndex-- },
                enabled = currentIndex != 0,
            ) {
                Icon(
                    imageVector = Icons.Filled.KeyboardArrowLeft,
                    contentDescription = "Back One"
                )
            }

            Box(modifier = Modifier
                .weight(1F, fill = false)
                .fillMaxWidth(), contentAlignment = Alignment.Center) {
                content(items[currentIndex])
            }

            FilledIconButton(
                onClick = { currentIndex++ },
                enabled = currentIndex != items.size - 1,
            ) {
                Icon(
                    imageVector = Icons.Filled.KeyboardArrowRight,
                    contentDescription = "Forward One"
                )
            }
        }
    }


}

@Preview
@Composable
private fun PreviewDark() {
    WatchBuddyTheme(darkTheme = true) {
        Surface {
            DetailsClickCarousel(items = listOf("Howdy", "Howdy"), title = "Howdy") {
                Text(text = it)
            }
        }
    }
}

@Preview
@Composable
private fun PreviewLight() {
    WatchBuddyTheme(darkTheme = false) {
        Surface {
            DetailsClickCarousel(items = listOf("Howdy", "Howdy"), title = "Howdy") {
                Text(text = it)
            }
        }
    }
}