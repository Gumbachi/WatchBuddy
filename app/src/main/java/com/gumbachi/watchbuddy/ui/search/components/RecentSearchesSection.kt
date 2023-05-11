package com.gumbachi.watchbuddy.ui.search.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gumbachi.watchbuddy.model.WatchBuddyID
import com.gumbachi.watchbuddy.model.enums.data.ReleaseStatus
import com.gumbachi.watchbuddy.model.interfaces.Cardable
import com.gumbachi.watchbuddy.ui.cards.EfficientPosterImage
import com.gumbachi.watchbuddy.ui.components.FixedText
import com.gumbachi.watchbuddy.ui.placeholders.NothingHerePlaceholder
import com.gumbachi.watchbuddy.ui.theme.WatchBuddyTheme

@Composable
fun <T : Cardable> RecentSearchesSection(
    recents: List<T>,
    onItemClick: (T) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(text = "Recently Viewed", style = MaterialTheme.typography.labelLarge)

        if (recents.isEmpty()) {
            NothingHerePlaceholder(
                text = "Nothing here yet\nRecently viewed media will appear here",
                modifier = Modifier.fillMaxWidth(),
                iconSize = 80.dp,
                textStyle = MaterialTheme.typography.labelLarge
            )
        } else {
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                items(recents) {
                    Column(
                        modifier = Modifier
                            .width(140.dp)
                            .clickable { onItemClick(it) }
                    ) {
                        EfficientPosterImage(
                            posterURL = it.posterURL,
                            aspectRatio = 0.75F,
                            modifier = Modifier.clip(RoundedCornerShape(12.dp))
                        )
                        FixedText(
                            text = it.title,
                            minLines = 2,
                            maxLines = 2,
                            style = MaterialTheme.typography.labelMedium,
                            modifier = Modifier.padding(vertical = 2.dp, horizontal = 4.dp)
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    WatchBuddyTheme() {
        RecentSearchesSection(
            onItemClick = {},
            recents = List(10) {
            object : Cardable {
                override val title: String
                    get() = "HOWDY HOWDY HOWDY"
                override val watchbuddyID: WatchBuddyID
                    get() = TODO("Not yet implemented")
                override val primaryDetail: String
                    get() = TODO("Not yet implemented")
                override val secondaryDetail: String
                    get() = TODO("Not yet implemented")
                override val posterURL: String
                    get() = ""
                override val score: Int
                    get() = TODO("Not yet implemented")
                override val releaseStatus: ReleaseStatus
                    get() = TODO("Not yet implemented")
            }
        })
    }
}