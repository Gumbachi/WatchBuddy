package com.gumbachi.watchbuddy.ui.details.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.AlertDialog
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import coil.compose.AsyncImage
import coil.request.ImageRequest

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun DetailsImageCarouselDialog(
    shown: Boolean,
    imageURLs: List<String>,
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit
) {

    if (!shown || imageURLs.isEmpty()) return

    var index by remember {
        mutableStateOf(0)
    }

    val interactionSource = remember {
        MutableInteractionSource()
    }

    AlertDialog(
        onDismissRequest = onDismissRequest,
        buttons = {},
        modifier = modifier,
        properties = DialogProperties(usePlatformDefaultWidth = false),
        backgroundColor = Color.Transparent,
        text = {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .clickable(
                        indication = null,
                        interactionSource = interactionSource
                    ) { onDismissRequest() },
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current).data(imageURLs[index])
                        .crossfade(true).build(),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.8F)
                )
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    FilledIconButton(
                        onClick = { index-- },
                        enabled = index != 0,
                    ) {
                        Icon(
                            imageVector = Icons.Filled.KeyboardArrowLeft,
                            contentDescription = "Back One"
                        )
                    }
                    FilledIconButton(
                        onClick = { index++ },
                        enabled = index != imageURLs.size - 1,
                    ) {
                        Icon(
                            imageVector = Icons.Filled.KeyboardArrowRight,
                            contentDescription = "Forward One"
                        )
                    }
                }
            }
        }
    )
}