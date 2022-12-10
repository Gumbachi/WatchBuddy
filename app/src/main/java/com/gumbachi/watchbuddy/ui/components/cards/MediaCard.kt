package com.gumbachi.watchbuddy.ui.components.cards

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
    modifier: Modifier = Modifier
) {
    when (cardStyle) {
        CardStyle.Normal -> {
            NormalMediaCard(
                imageURL = cardData.posterURL,
                headline = cardData.title,
                primarySubtitle = cardData.primaryDetail,
                secondarySubtitle = cardData.secondaryDetail,
                score = cardData.score.toString(format = scoreFormat, decorated = true),
                statusText = cardData.releaseStatus.text,
                statusColor = cardData.releaseStatus.color,
                progress = cardData.progress,
                onClick = onClick,
                onLongClick = onLongClick,
                modifier = modifier
            )
        }
        CardStyle.Compact -> {
            CompactMediaCard(
                imageURL = cardData.posterURL,
                headline = cardData.title,
                primarySubtitle = cardData.primaryDetail,
                secondarySubtitle = cardData.secondaryDetail,
                score = cardData.score.toString(format = scoreFormat, decorated = true),
                progress = cardData.progress,
                statusColor = cardData.releaseStatus.color,
                onClick = onClick,
                onLongClick = onLongClick,
                modifier = modifier
            )
        }
    }
}