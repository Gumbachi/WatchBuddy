package com.gumbachi.watchbuddy.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gumbachi.watchbuddy.model.enums.data.WatchStatus
import com.gumbachi.watchbuddy.ui.theme.WatchBuddyTheme

@Composable
fun WatchStatusTabRow(
    selected: WatchStatus,
    onSelectedChange: (WatchStatus) -> Unit,
    modifier: Modifier = Modifier
) {
    ScrollableTabRow(
        modifier = modifier.fillMaxWidth(),
        selectedTabIndex = selected.toIndex(),
        indicator = {
            Spacer(
                modifier = Modifier
                    .tabIndicatorOffset(it[selected.toIndex()])
                    .padding(horizontal = 16.dp)
                    .height(3.dp)
                    .background(
                        color = TabRowDefaults.contentColor,
                        shape = RoundedCornerShape(100, 100)
                    )
            )
        }
    ) {
        WatchStatus.values().forEachIndexed { index, status ->
            Tab(
                text = { Text(text = status.toString()) },
                selected = selected.toIndex() == index,
                onClick = { onSelectedChange(status) },
                unselectedContentColor = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}


@Composable
fun WatchbuddyTabRow(
    selectedTab: Int,
    tabTitles: List<String>,
    modifier: Modifier = Modifier,
    onTabChange: (Int) -> Unit = {}
) {
    ScrollableTabRow(
        selectedTabIndex = selectedTab,
        indicator = {
            Spacer(
                modifier = modifier
                    .tabIndicatorOffset(it[selectedTab])
                    .padding(horizontal = 16.dp)
                    .height(3.dp)
                    .background(
                        color = TabRowDefaults.contentColor,
                        shape = RoundedCornerShape(100, 100)
                    )
            )
        }
    ) {
        tabTitles.forEachIndexed { index, name ->
            Tab(
                text = { Text(text = name) },
                selected = selectedTab == index,
                onClick = { onTabChange(index) },
                unselectedContentColor = MaterialTheme.colorScheme.onSurfaceVariant

            )
        }
    }
}

@Preview
@Composable
fun TabRowPreview(darkMode: Boolean = false) {
    WatchBuddyTheme(darkTheme = darkMode) {
        Surface {
            WatchbuddyTabRow(
                selectedTab = 1,
                tabTitles = listOf("Watching", "Planning", "Completed")
            )
        }
    }
}

@Preview
@Composable
fun TabRowPreviewDark() {
    TabRowPreview(darkMode = true)
}
