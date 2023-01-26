package com.gumbachi.watchbuddy.ui.details.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BrokenImage
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage

@Composable
fun DetailsCreditCard(
    imageURL: String,
    contentDescription: String,
    primaryDetail: String,
    secondaryDetail: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.width(150.dp).height(260.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        SubcomposeAsyncImage(
            model = imageURL,
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
            contentDescription = contentDescription,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(150.dp, 200.dp)
//                .clip(RoundedCornerShape(12.dp)),
        )
        Column(modifier = Modifier.padding(start = 4.dp, end = 4.dp, bottom = 4.dp)) {
            Text(text = primaryDetail, style = MaterialTheme.typography.titleSmall)
            Text(
                text = secondaryDetail,
                style = MaterialTheme.typography.labelMedium,
                color = Color.Gray,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}