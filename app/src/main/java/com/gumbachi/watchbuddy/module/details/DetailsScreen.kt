package com.gumbachi.watchbuddy.module.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.gumbachi.watchbuddy.model.WatchBuddyID
import com.gumbachi.watchbuddy.ui.components.WatchbuddyScaffold
import com.gumbachi.watchbuddy.ui.toolbars.WatchbuddyBackAppBar
import com.gumbachi.watchbuddy.ui.cards.PosterImage
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewDetailsScreen(
    watchbuddyID: WatchBuddyID,
    modifier: Modifier = Modifier,
    viewModel: DetailsViewModel = koinViewModel(),
    onBackClicked: () -> Unit = {},
) {

    val state by viewModel.uiState.collectAsState()

    // Load details
    LaunchedEffect(Unit) {
        viewModel.loadDetailsFor(watchbuddyID)
    }

    WatchbuddyScaffold(
        isLoading = state.loading,
        error = state.error,
        topBar = {
            WatchbuddyBackAppBar(
                title = "${watchbuddyID.type} Details",
                onBackClicked = onBackClicked
            )
        },
    ) {
        state.details?.let { details ->
            DetailsScreenBanner(imageURL = details.backdropURL)
            DetailsScreenTitle(title = details.title)
            DetailsScreenPosterAndDetails(
                imageURL = details.posterURL,
                shortDetails = details.shortDetails()
            )
            details.overview?.let { overview ->
                DetailsScreenOverviewSection(overview = overview)
            }
        }
    }
}

@Composable
private fun DetailsScreenBanner(
    imageURL: String,
    modifier: Modifier = Modifier
) {
    SubcomposeAsyncImage(
        model = imageURL,
        contentDescription = null,
        contentScale = ContentScale.Crop,
        alignment = Alignment.TopCenter,
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp),
    )
}

@Composable
private fun DetailsScreenTitle(
    title: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium,
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
private fun DetailsScreenPosterAndDetails(
    imageURL: String,
    shortDetails: List<String>,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 24.dp)
    ) {
        PosterImage(
            posterURL = imageURL,
            matchConstraintByHeight = false,
            modifier = Modifier
                .width(170.dp)
                .padding(4.dp)
                .clip(RoundedCornerShape(10.dp))
        )
        Column {
            shortDetails.forEach {
                Text(
                    text = it,
                    style = MaterialTheme.typography.labelSmall
                )
            }
        }
    }
}

@Composable
private fun DetailsScreenOverviewSection(
    overview: String,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = "Overview",
            style = MaterialTheme.typography.labelLarge
        )
        Text(
            text = overview,
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Composable
private fun DetailsScreenCreditsSection(

) {

}




























//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun DetailsScreen(
//    watchbuddyID: WatchbuddyID,
//    modifier: Modifier = Modifier,
//    viewModel: DetailsViewModel = koinViewModel(),
//    onBackClick: () -> Unit = {},
//) {
//    val state = viewModel.state
//
//    LaunchedEffect(Unit) {
//        viewModel.loadDetails(watchbuddyID)
//    }
//
//    Scaffold(
//        topBar = {
//            WatchbuddyBackAppBar(
//                onBackClicked = onBackClick,
//                title = state.details?.title ?: "",
//                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
//            )
//        }
//    ) { innerPadding ->
//
//        val newPadding = PaddingValues(
//            start = innerPadding.calculateStartPadding(LayoutDirection.Ltr),
//            end = innerPadding.calculateEndPadding(LayoutDirection.Ltr),
//            bottom = innerPadding.calculateBottomPadding()
//        )
//
//        if (state.loading) {
//            Box(
//                modifier = modifier.fillMaxSize(),
//                contentAlignment = Alignment.Center
//            ) {
//                CircularProgressIndicator()
//            }
//            return@Scaffold
//        }
//
//        state.details?.let {
//            DetailsScreenContent(
//                details = it,
//                modifier = modifier.padding(newPadding),
//                contentOffset = innerPadding.calculateTopPadding()
//            )
//        }
//    }
//}
//
//
//@Composable
//private fun DetailsScreenContent(
//    details: Detailable,
//    modifier: Modifier = Modifier,
//    contentOffset: Dp = 0.dp,
//) {
//    Column(modifier = modifier.fillMaxSize()) {
//        SubcomposeAsyncImage(
//            model = details.backdropURL,
//            contentDescription = null,
//            contentScale = ContentScale.Crop,
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(200.dp),
//            alignment = Alignment.TopCenter
//        )
//
//        Column(modifier = Modifier.padding(top = contentOffset)) {
////                Text(
////                    text = details.title,
////                    style = MaterialTheme.typography.titleMedium,
////                    textAlign = TextAlign.Center,
////                    modifier = Modifier.fillMaxWidth()
////                )
//            Row(
//                verticalAlignment = Alignment.CenterVertically,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(bottom = 24.dp)
//            ) {
//
//                PosterImage(
//                    posterURL = details.posterURL,
//                    matchConstraintByHeight = false,
//                    modifier = Modifier
//                        .width(170.dp)
//                        .padding(4.dp)
//                        .clip(RoundedCornerShape(10.dp))
//                )
//                Column {
//                    details.shortDetails().forEach {
//                        Text(
//                            text = it,
//                            style = MaterialTheme.typography.labelSmall
//                        )
//                    }
//                }
//            }
//        }
//
//        details.overview?.let {
//            // Overview Section
//            Text(
//                text = "Overview",
//                style = MaterialTheme.typography.labelLarge
//            )
//            Text(
//                text = it,
//                style = MaterialTheme.typography.bodySmall
//            )
//        }
//    }
//}
//
//
//@Preview
//@Composable
//fun DetailsContentPreview() {
//    WatchBuddyTheme {
//        Surface {
//            DetailsScreenContent(details = Detailable.Dummy(title = "Made in Abyss: The Golden City of the Scorching Sun"))
//        }
//    }
//}
