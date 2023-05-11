package com.gumbachi.watchbuddy.module.settings

import android.content.Intent
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.gumbachi.watchbuddy.ui.navigation.WatchbuddyMainDestination
import org.koin.androidx.compose.koinViewModel

private val destination = WatchbuddyMainDestination.Settings

fun NavGraphBuilder.settingsScreen() {
    composable(
        route = destination.route,
        deepLinks = listOf(navDeepLink {
            uriPattern = "https://gumbachi.com/{data}"
            action = Intent.ACTION_VIEW
        }),
        arguments = listOf(
            navArgument("data") {
                type = NavType.StringType
                defaultValue = "howdy"
            }
        )
    ) {

        val extra = it.arguments?.getString("data")

        println("The extra data provided is $extra")

        SettingsScreen(viewModel = koinViewModel())
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
