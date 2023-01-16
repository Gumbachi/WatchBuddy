package com.gumbachi.watchbuddy.module.details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.gumbachi.watchbuddy.model.WatchBuddyID
import com.gumbachi.watchbuddy.model.toWatchbuddyID
import com.gumbachi.watchbuddy.ui.navigation.WatchBuddySecondaryDestination
import org.koin.androidx.compose.koinViewModel

private val destination = WatchBuddySecondaryDestination.Details

fun NavGraphBuilder.detailsScreen(
    navigateBack: () -> Unit
) {
    composable(
        route = destination.route,
        arguments = listOf(navArgument("id") { type = NavType.StringType })
    ) {
        it.arguments?.getString("id")?.let { id ->
            NewDetailsScreen(
                watchbuddyID = id.toWatchbuddyID(),
                viewModel = koinViewModel(),
                onBackClicked = navigateBack,
            )
        } ?: run {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Something Went Wrong")
                Button(onClick = navigateBack) {
                    Text(text = "Go Back")
                }
            }
        }
    }
}

fun NavController.navigateToDetails(id: WatchBuddyID) {
    val route = destination.buildRoute(id = id)
    navigate(route) {
        launchSingleTop = true
    }
}