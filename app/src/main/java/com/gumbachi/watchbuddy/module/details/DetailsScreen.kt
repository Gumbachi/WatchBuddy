package com.gumbachi.watchbuddy.module.details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.gumbachi.watchbuddy.model.WatchbuddyID
import com.gumbachi.watchbuddy.model.interfaces.Detailable
import com.gumbachi.watchbuddy.model.interfaces.DetailableImpl
import com.gumbachi.watchbuddy.ui.components.appbars.WatchbuddyBackAppBar
import com.gumbachi.watchbuddy.ui.components.cards.PosterImage
import com.gumbachi.watchbuddy.ui.theme.WatchBuddyTheme
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(
    watchbuddyID: WatchbuddyID,
    modifier: Modifier = Modifier,
    viewModel: DetailsViewModel = koinViewModel(),
    onBackClick: () -> Unit = {},
) {
    val state = viewModel.state

    LaunchedEffect(Unit) {
        viewModel.loadDetails(watchbuddyID)
    }

    Scaffold(
        topBar = {
            WatchbuddyBackAppBar(
                onBackClicked = onBackClick,
                title = state.details?.title ?: "",
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        }
    ) { innerPadding ->

        val newPadding = PaddingValues(
            start = innerPadding.calculateStartPadding(LayoutDirection.Ltr),
            end = innerPadding.calculateEndPadding(LayoutDirection.Ltr),
            bottom = innerPadding.calculateBottomPadding()
        )

        if (state.loading) {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
            return@Scaffold
        }

        state.details?.let {
            DetailsScreenContent(
                details = it,
                modifier = modifier.padding(newPadding),
                contentOffset = innerPadding.calculateTopPadding()
            )
        }
    }
}


@Composable
private fun DetailsScreenContent(
    details: Detailable,
    modifier: Modifier = Modifier,
    contentOffset: Dp = 0.dp,
) {
    Column(modifier = modifier.fillMaxSize()) {
        SubcomposeAsyncImage(
            model = details.backdropURL,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxWidth().height(200.dp),
            alignment = Alignment.TopCenter
        )

        Column(modifier = Modifier.padding(top = contentOffset)) {
//                Text(
//                    text = details.title,
//                    style = MaterialTheme.typography.titleMedium,
//                    textAlign = TextAlign.Center,
//                    modifier = Modifier.fillMaxWidth()
//                )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp)
            ) {

                PosterImage(
                    posterURL = details.posterURL,
                    matchConstraintByHeight = false,
                    modifier = Modifier
                        .width(170.dp)
                        .padding(4.dp)
                        .clip(RoundedCornerShape(10.dp))
                )
                Column {
                    details.shortDetails().forEach {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                }
            }
        }

        details.overview?.let {
            // Overview Section
            Text(
                text = "Overview",
                style = MaterialTheme.typography.labelLarge
            )
            Text(
                text = it,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}


@Preview
@Composable
fun DetailsContentPreview() {
    WatchBuddyTheme {
        Surface {
            DetailsScreenContent(details = DetailableImpl(title = "Made in Abyss: The Golden City of the Scorching Sun"))
        }
    }
}
