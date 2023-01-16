package com.gumbachi.watchbuddy.ui.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BrokenImage
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.gumbachi.watchbuddy.model.WatchBuddyID
import com.gumbachi.watchbuddy.model.enums.data.ReleaseStatus
import com.gumbachi.watchbuddy.model.interfaces.Cardable
import com.gumbachi.watchbuddy.ui.theme.WatchBuddyTheme

@Composable
fun RecentSearchesSection(
    recents: List<Cardable>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(text = "Recently Viewed", style = MaterialTheme.typography.labelLarge)
        LazyRow(modifier = Modifier.fillMaxWidth()) {
            items(recents) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(2.dp),
                    modifier = Modifier
                        .width(140.dp)
                        .padding(horizontal = 6.dp)
                ) {
                    SubcomposeAsyncImage(
                        model = it.posterURL,
                        loading = {
                            Box(contentAlignment = Alignment.Center) {
                                CircularProgressIndicator()
                            }
                        },
                        error = {
                            Image(
                                imageVector = Icons.Filled.BrokenImage,
                                contentDescription = "Image failed to load"
                            )
                        },
                        contentDescription = null,
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier
                            .aspectRatio(0.75F)
                            .clip(RoundedCornerShape(12.dp))
                    )
                    Text(
                        text = it.title,
                        maxLines = 2,
                        style = MaterialTheme.typography.labelMedium,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    WatchBuddyTheme() {
        RecentSearchesSection(recents = List(10) {object : Cardable {
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
        }}  )
    }
}