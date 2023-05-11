package com.gumbachi.watchbuddy.ui.cards

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.gumbachi.watchbuddy.model.enums.data.MediaType
import com.gumbachi.watchbuddy.model.enums.data.WatchStatus
import com.gumbachi.watchbuddy.model.interfaces.Media
import com.gumbachi.watchbuddy.model.settings.CardSettings
import com.gumbachi.watchbuddy.ui.placeholders.NothingHerePlaceholder

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun <T: Media> LazyMediaGrid(
    items: List<T>,
    settings: CardSettings,
    onItemClick: (T) -> Unit,
    onItemLongClick: (T) -> Unit,
    modifier: Modifier = Modifier
) {

    val gridState = rememberLazyGridState()

    if (items.isEmpty()) {
        NothingHerePlaceholder(modifier = Modifier.fillMaxSize())
    } else {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(settings.style.requiredWidth),
            horizontalArrangement = Arrangement.Center,
            state = gridState,
            contentPadding = PaddingValues(4.dp),
            modifier = modifier.fillMaxSize()
        ) {
            items(
                items = items,
                key = { it.watchbuddyID.toString() }
            ) { media ->

                val showScore = when (settings.showScoreOnPlanned) {
                    true -> true
                    false -> media.watchStatus != WatchStatus.Planning
                }

                val showProgress = when (settings.showProgressOnPlanned) {
                    true -> media.type != MediaType.Movie
                    false -> media.watchStatus != WatchStatus.Planning
                }

                MediaCard(
                    cardData = media,
                    cardStyle = settings.style,
                    scoreFormat = settings.scoreFormat,
                    showApi = settings.showApi,
                    onClick = { onItemClick(media) },
                    onLongClick = { onItemLongClick(media) },
                    modifier = Modifier.animateItemPlacement(),
                    showScore = showScore,
                    showProgress = showProgress
                )
            }
        }
    }
}