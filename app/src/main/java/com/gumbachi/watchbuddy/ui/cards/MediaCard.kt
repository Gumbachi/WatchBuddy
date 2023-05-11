package com.gumbachi.watchbuddy.ui.cards

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.gumbachi.watchbuddy.model.enums.configuration.CardStyle
import com.gumbachi.watchbuddy.model.enums.configuration.ScoreFormat
import com.gumbachi.watchbuddy.model.interfaces.Cardable
import com.gumbachi.watchbuddy.utils.toString

@Composable
fun MediaCard(
    cardData: Cardable,
    cardStyle: CardStyle,
    scoreFormat: ScoreFormat,
    onClick: () -> Unit,
    onLongClick: () -> Unit,
    modifier: Modifier = Modifier,
    showApi: Boolean = true,
    showScore: Boolean = true,
    showProgress: Boolean = true,
    isSaved: Boolean? = null,
    onProgressClick: (() -> Unit)? = null
) {

    val score = when (showScore) {
        true -> cardData.score.toString(format = scoreFormat, decorated = true)
        false -> null
    }
    
    val progress = when (showProgress) {
        true -> cardData.progress
        false -> null
    }

    val api = when (showApi) {
        true -> cardData.watchbuddyID.api
        false -> null
    }

    when (cardStyle) {
        CardStyle.Normal -> {
            NormalMediaCard(
                imageURL = cardData.posterURL,
                headline = cardData.title,
                primarySubtitle = cardData.primaryDetail,
                secondarySubtitle = cardData.secondaryDetail,
                score = score,
                statusText = cardData.releaseStatus.name,
                statusColor = cardData.releaseStatus.color,
                progress = progress,
                onClick = onClick,
                onLongClick = onLongClick,
                onProgressClick = onProgressClick,
                api = api,
                isSaved = isSaved,
                modifier = modifier
            )
        }
        CardStyle.Compact -> {
            CompactMediaCard(
                imageURL = cardData.posterURL,
                headline = cardData.title,
                primarySubtitle = cardData.primaryDetail,
                secondarySubtitle = cardData.secondaryDetail,
                score = score,
                progress = progress,
                api = api,
                isSaved = isSaved,
                statusColor = cardData.releaseStatus.color,
                onClick = onClick,
                onLongClick = onLongClick,
                onProgressClick = onProgressClick,
                modifier = modifier
            )
        }
    }
}