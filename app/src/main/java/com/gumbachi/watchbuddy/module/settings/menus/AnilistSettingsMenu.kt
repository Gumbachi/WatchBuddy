package com.gumbachi.watchbuddy.module.settings.menus

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.NoAdultContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.gumbachi.watchbuddy.model.enums.configuration.AnilistTitleLanguage
import com.gumbachi.watchbuddy.model.settings.AnilistSettings
import com.gumbachi.watchbuddy.ui.components.SingleSelectDropdown
import com.gumbachi.watchbuddy.ui.components.SpacedSection
import com.gumbachi.watchbuddy.ui.settings.components.SettingsSwitch

@Composable
fun AnilistSettingsMenu(
    settings: AnilistSettings,
    updateEnabled: (Boolean) -> Unit,
    updatePreferredLanguage: (AnilistTitleLanguage) -> Unit,
    updateAdult: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {

    Column(
        modifier = modifier.padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        SettingsSwitch(
            title = "Enabled",
            icon = Icons.Filled.Check,
            description = "AniList items will no longer show if disabled. Saved Anilist items will not be affected",
            checked = settings.enabled,
            onCheckedChange = { updateEnabled(it) }
        )
        SettingsSwitch(
            title = "Adult Content",
            icon = Icons.Filled.NoAdultContent,
            description = "Include adult content in searches",
            checked = settings.adult,
            onCheckedChange = { updateAdult(it) }
        )
        SpacedSection(label = "Preferred Language", icon = Icons.Filled.Language, description = "AniList media will have titles in the selected language") {
            SingleSelectDropdown(
                selected = settings.preferredLanguage,
                onSelectedChange = updatePreferredLanguage,
                options = AnilistTitleLanguage.values().toList()
            )
        }
    }
}