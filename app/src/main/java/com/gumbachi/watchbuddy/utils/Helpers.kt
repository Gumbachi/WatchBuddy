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

fun String.toTMDBImageURL() = "https://www.themoviedb.org/t/p/w500$this"
fun String?.toTMDBImageURLOrBlank() = this?.let { "https://www.themoviedb.org/t/p/w500$it" } ?: ""

fun Int?.formatRuntime() = this?.let { "${it / 60}h ${it % 60}m" } ?: "??h ??m"

const val LONG_MEDIA_TITLE = "This is a long movie/show title that is meant for preview testing and should probably be longer than just one line unless there is a metric ton of space which is not usually the case"