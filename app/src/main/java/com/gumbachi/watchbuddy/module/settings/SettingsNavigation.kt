package com.gumbachi.watchbuddy.module.settings

import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.gumbachi.watchbuddy.ui.navigation.WatchbuddyMainDestination
import org.koin.androidx.compose.koinViewModel

private val destination = WatchbuddyMainDestination.Settings

fun NavGraphBuilder.settingsScreen() {
    composable(destination.route) {
        SettingsScreen(
            viewModel = koinViewModel()
        )
    }
}

fun NavController.navigateToSettings() {
    if (currentDestination?.route == destination.route) return

    navigate(destination.route) {
        popUpTo(graph.findStartDestination().id) {
            inclusive = true
            saveState = true
        }
        restoreState = true
        launchSingleTop = true
    }
}