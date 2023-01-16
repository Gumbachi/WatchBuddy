package com.gumbachi.watchbuddy.ui.settings.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.MainAxisAlignment
import com.gumbachi.watchbuddy.model.enums.data.WatchStatus
import com.gumbachi.watchbuddy.ui.components.MediaTabRow
import com.gumbachi.watchbuddy.ui.theme.getElevation

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WatchStatusTabCustomizer(
    hiddenCategories: Collection<WatchStatus>,
    onHiddenCategoriesChange: (Collection<WatchStatus>) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.surfaceColorAtElevation(getElevation(1)))
            .padding(16.dp)
    ) {

        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {

            val shownTabs by remember(hiddenCategories) {
                derivedStateOf { WatchStatus.values().toList() - hiddenCategories.toSet() }
            }

            Text(text = "Preview")

            MediaTabRow(
                selected = 0,
                tabs = shownTabs,
                onSelectedChange = { _, _ -> }
            )

            Text(text = "Categories")

            FlowRow(
                mainAxisSpacing = 8.dp,
                mainAxisAlignment = MainAxisAlignment.Start,
                modifier = Modifier.fillMaxWidth()
            ) {
                WatchStatus.values().forEach { status ->
                    FilterChip(
                        selected = status !in hiddenCategories,
                        label = { Text(text = status.toString()) },
                        leadingIcon = {
                            if (status !in hiddenCategories) {
                                Icon(imageVector = Icons.Filled.Check, contentDescription = null, modifier = Modifier.size(18.dp))
                            }
                        },
                        onClick = {
                            when (status in hiddenCategories) {
                                // Move status to shown categories
                                true -> {
                                    onHiddenCategoriesChange(hiddenCategories - status)
                                }
                                // Hide category
                                false -> {
                                    if (shownTabs.size > 1) {
                                        onHiddenCategoriesChange(hiddenCategories + status)
                                    }
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}
