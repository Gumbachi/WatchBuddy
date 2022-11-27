package com.gumbachi.watchbuddy.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
fun Toast(
    text: String,
    length: Int
) {
    val context = LocalContext.current
    android.widget.Toast.makeText(context, text, length).show()
}