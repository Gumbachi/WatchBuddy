package com.gumbachi.watchbuddy.utils

import androidx.compose.material3.*
import androidx.compose.ui.graphics.Color
import com.gumbachi.watchbuddy.ui.theme.getElevation

suspend fun displaySnackbar(
    message: String,
    actionLabel: String? = null,
    snackbarState: SnackbarHostState,
    onDismiss: suspend () -> Unit = {},
    onAction: suspend () -> Unit
) {
    val result = snackbarState.showSnackbar(
        message = message,
        actionLabel = actionLabel,
        duration = SnackbarDuration.Short
    )
    when (result) {
        SnackbarResult.Dismissed -> onDismiss()
        SnackbarResult.ActionPerformed -> onAction()
    }
}

fun ColorScheme.surfaceColorAtElevation(level: Int): Color {
    return this.surfaceColorAtElevation(getElevation(level))
}