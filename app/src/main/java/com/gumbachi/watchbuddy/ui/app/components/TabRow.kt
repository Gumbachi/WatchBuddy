package com.gumbachi.watchbuddy.ui.app.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gumbachi.watchbuddy.ui.theme.WatchBuddyTheme

@Composable
fun WatchbuddyTabRow(
    selectedTab: Int,
    tabTitles: List<String>,
    modifier: Modifier = Modifier,
    onTabChange: (Int) -> Unit = {}
) {
    ScrollableTabRow(
        modifier = modifier,
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
