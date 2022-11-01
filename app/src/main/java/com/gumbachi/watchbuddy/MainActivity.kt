package com.gumbachi.watchbuddy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.gumbachi.watchbuddy.ui.app.WatchbuddyApp
import com.gumbachi.watchbuddy.ui.theme.WatchBuddyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WatchBuddyTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    WatchbuddyApp()
                }
            }
        }
    }
}
