package com.gumbachi.watchbuddy.ui.settings.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.gumbachi.watchbuddy.ui.components.SpacedSection
import com.gumbachi.watchbuddy.ui.theme.WatchBuddyTheme

@Composable
fun SettingsSwitch(
    title: String,
    icon: ImageVector,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    description: String? = null,
) {
    SpacedSection(label = title, icon = icon, description = description) {
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange
        )
    }
}

@Preview
@Composable
private fun PreviewDark() {
    WatchBuddyTheme(darkTheme = true) {
        Surface() {
            SettingsSwitch(title = "Settings", description = "Settings Description That is reather long please dont pus the switch off the screen", icon = Icons.Filled.Settings, checked = true, onCheckedChange = {})
        }
    }
}

@Preview
@Composable
private fun PreviewLight() {
    WatchBuddyTheme(darkTheme = false) {
        Surface() {
            SettingsSwitch(title = "Settings", description = "Settings Description", icon = Icons.Filled.Settings, checked = true, onCheckedChange = {})
        }
    }
}