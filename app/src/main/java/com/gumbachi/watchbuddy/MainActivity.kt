package com.gumbachi.watchbuddy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.gumbachi.watchbuddy.database.WatchbuddyDB
import com.gumbachi.watchbuddy.ui.theme.WatchBuddyTheme
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking


class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val startDestination = runBlocking {
            WatchbuddyDB().getUserSettingsFlow().first().general.startingDestination
        }

        setContent {
            WatchBuddyTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    Watchbuddy(startDestination = startDestination)
                }
            }
        }
    }
}
