package com.gumbachi.watchbuddy.module.settings.menus

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.NoAdultContent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.web.WebView
import com.google.accompanist.web.rememberWebViewState
import com.gumbachi.watchbuddy.BuildConfig
import com.gumbachi.watchbuddy.model.enums.configuration.AnilistTitleLanguage
import com.gumbachi.watchbuddy.model.settings.AnilistSettings
import com.gumbachi.watchbuddy.ui.components.SingleSelectDropdown
import com.gumbachi.watchbuddy.ui.components.SpacedSection
import com.gumbachi.watchbuddy.ui.settings.components.SettingsSwitch
import com.gumbachi.watchbuddy.ui.theme.WatchBuddyTheme

@SuppressLint("SetJavaScriptEnabled")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun AnilistSettingsMenu(
    settings: AnilistSettings,
    updateEnabled: (Boolean) -> Unit,
    updatePreferredLanguage: (AnilistTitleLanguage) -> Unit,
    updateAdult: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {

    val uriHandler = LocalUriHandler.current
    val authURI =
        "https://anilist.co/api/v2/oauth/authorize?client_id=${BuildConfig.ANILIST_CLIENT_ID}&response_type=token"

    val webState = rememberWebViewState(url = authURI)
    var showWebView by remember { mutableStateOf(false) }

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
        SpacedSection(
            label = "Preferred Language",
            icon = Icons.Filled.Language,
            description = "AniList media will have titles in the selected language"
        ) {
            SingleSelectDropdown(
                selected = settings.preferredLanguage,
                onSelectedChange = updatePreferredLanguage,
                options = AnilistTitleLanguage.values().toList()
            )
        }



        LaunchedEffect(webState.lastLoadedUrl) {
            if (webState.lastLoadedUrl?.startsWith("https://gumbachi.com") == true) {
                println("WE MADE IT ${webState.lastLoadedUrl}")
                showWebView = false
            }
        }

        val webViewAlpha by animateFloatAsState(
            if (webState.isLoading) 0F else 1F,
            animationSpec = tween(500, delayMillis = 1000)
        )

        OutlinedCard(modifier = Modifier.fillMaxWidth()) {
            AnimatedContent(targetState = showWebView) { inWebView ->
                if (inWebView) {
                    Box(
                        modifier = Modifier.height(410.dp),
                        contentAlignment = Alignment.Center
                    ) {
//                        if (webState.isLoading || webState.loadingState == LoadingState.Initializing) {
//                            CircularProgressIndicator()
//                        }
                        WebView(
                            state = webState,
                            onCreated = {
                                it.settings.javaScriptEnabled = true
                                it.settings.domStorageEnabled = true
                            },
                            modifier = Modifier.graphicsLayer { alpha = webViewAlpha }
                        )
                    }
                } else {
                    Text(
                        text = "Sync AniList",
                        style = MaterialTheme.typography.displaySmall,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { showWebView = true },
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

//        if (showWebView) {
//            AlertDialog(
//                onDismissRequest = { showWebView = false },
//                properties = DialogProperties(usePlatformDefaultWidth = false)
//            ) {
//
//            }
//        }
    }


}

@Preview
@Composable
private fun PreviewDark(dark: Boolean = true) {
    WatchBuddyTheme(darkTheme = dark) {
        Surface {
            AnilistSettingsMenu(
                settings = AnilistSettings(),
                updateEnabled = {},
                updatePreferredLanguage = {},
                updateAdult = {}
            )
        }
    }
}

@Preview
@Composable
private fun PreviewLight() {
    PreviewDark(dark = false)
}