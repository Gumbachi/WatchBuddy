package com.gumbachi.watchbuddy.utils

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import com.gumbachi.watchbuddy.model.enums.data.Source

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