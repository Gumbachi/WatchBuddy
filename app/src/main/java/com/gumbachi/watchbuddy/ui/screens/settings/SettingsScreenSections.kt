package com.gumbachi.watchbuddy.ui.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.gumbachi.watchbuddy.ui.theme.WatchBuddyTheme

@Composable
fun SettingsScreenSections(
    modifier: Modifier = Modifier,
    sections: @Composable ColumnScope.() -> Unit
) {

    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        sections()
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SettingsScreenOption(
    title: String,
    description: String,
    icon: ImageVector,
    modifier: Modifier = Modifier,
    menuContent: @Composable ColumnScope.() -> Unit
) {

    var menuShown by remember {
        mutableStateOf(false)
    }

    Column(modifier = modifier
        .fillMaxWidth()
        .clickable { menuShown = true }) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(12.dp)) {
            Icon(imageVector = icon, contentDescription = null)
            Spacer(modifier = Modifier.width(12.dp))
            Column() {
                Text(text = title, style = MaterialTheme.typography.labelLarge)
                Text(text = description, style = MaterialTheme.typography.labelMedium)
            }
        }
        Divider()
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
                Box(modifier = Modifier.fillMaxWidth()) {
                    IconButton(onClick = { menuShown = false }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Go Back",
                            modifier = Modifier.align(Alignment.CenterStart).padding(8.dp)
                        )
                    }
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                menuContent()
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun SectionPreview() {
    WatchBuddyTheme(darkTheme = true) {
        Surface {

            SettingsScreenSections() {
                SettingsScreenOption(
                    title = "Card Customization",
                    description = "Change style, score format, etc.",
                    icon = Icons.Filled.Settings
                ) {}
                SettingsScreenOption(
                    title = "Card Customization",
                    description = "Change style, score format, etc.",
                    icon = Icons.Filled.Settings
                ) {}
                SettingsScreenOption(
                    title = "Card Customization",
                    description = "Change style, score format, etc.",
                    icon = Icons.Filled.Settings
                ) {}
            }


        }
    }
}