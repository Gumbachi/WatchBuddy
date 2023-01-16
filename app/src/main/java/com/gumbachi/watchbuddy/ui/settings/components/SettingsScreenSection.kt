package com.gumbachi.watchbuddy.ui.settings.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.MainAxisAlignment
import com.google.accompanist.flowlayout.SizeMode

@Composable
fun SettingsScreenSection(
    title: String,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit = {}
) {

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        FlowRow(
            mainAxisAlignment = MainAxisAlignment.Start,
            mainAxisSpacing = 8.dp,
            crossAxisSpacing = 8.dp,
            mainAxisSize = SizeMode.Wrap,
        ) {
            content()
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SettingsScreenItem(
    title: String,
    icon: Painter,
    modifier: Modifier = Modifier,
    menuContent: @Composable ColumnScope.() -> Unit
) {

    var menuShown by rememberSaveable { mutableStateOf(false) }

    OutlinedButton(
        onClick = { menuShown = true },
        shape = MaterialTheme.shapes.medium,
        contentPadding = PaddingValues(horizontal = 0.dp, vertical = 12.dp),
        modifier = modifier.width(160.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(vertical = 12.dp)
        ) {
            Icon(painter = icon, contentDescription = title, Modifier.size(32.dp))
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.labelMedium,
                textAlign = TextAlign.Center,
                maxLines = 2
            )
        }
    }

    if (menuShown) {
        Dialog(
            onDismissRequest = { menuShown = false },
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = MaterialTheme.colorScheme.surface)
            ) {
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)) {
                    IconButton(onClick = { menuShown = false }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Go Back",
                            modifier = Modifier
                                .align(Alignment.CenterStart)
                                .padding(8.dp)
                        )
                    }
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                menuContent()
            }
        }
    }
}

