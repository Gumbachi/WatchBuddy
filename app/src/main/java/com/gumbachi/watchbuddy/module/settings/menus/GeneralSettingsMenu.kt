package com.gumbachi.watchbuddy.module.settings.menus

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.gumbachi.watchbuddy.model.settings.GeneralSettings
import com.gumbachi.watchbuddy.ui.components.SingleSelectDropdown
import com.gumbachi.watchbuddy.ui.components.SpacedSection
import com.gumbachi.watchbuddy.ui.navigation.WatchbuddyMainDestination

@Composable
fun GeneralSettingMenu(
    settings: GeneralSettings,
    updateStartingDestination: (WatchbuddyMainDestination) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        SpacedSection(label = "Starting Destination", icon = Icons.Default.MyLocation) {
            SingleSelectDropdown(
                selected = settings.startingDestination,
                onSelectedChange = updateStartingDestination,
                options = WatchbuddyMainDestination.values()
            )
        }
    }
}