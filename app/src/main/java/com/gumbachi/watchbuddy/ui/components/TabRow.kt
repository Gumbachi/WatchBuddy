package com.gumbachi.watchbuddy.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.gumbachi.watchbuddy.model.enums.data.WatchStatus
import com.gumbachi.watchbuddy.ui.theme.WatchBuddyTheme

@Composable
private fun MeasureUnconstrainedViewWidth(
    viewToMeasure: @Composable () -> Unit,
    content: @Composable (measuredWidth: Dp) -> Unit,
) {
    SubcomposeLayout { constraints ->
        val measuredWidth = subcompose("viewToMeasure", viewToMeasure)[0]
            .measure(Constraints()).width.toDp()

        val contentPlaceable = subcompose("content") {
            content(measuredWidth)
        }[0].measure(constraints)
        layout(contentPlaceable.width, contentPlaceable.height) {
            contentPlaceable.place(0, 0)
        }
    }
}



@Composable
fun MediaTabRow(
    selected: Int,
    tabs: Collection<WatchStatus>,
    onSelectedChange: (Int, WatchStatus) -> Unit,
    modifier: Modifier = Modifier
) {

    MeasureUnconstrainedViewWidth(viewToMeasure = {
        Row {
            tabs.forEachIndexed { index, status ->
                Tab(
                    text = { Text(text = status.toString()) },
                    selected = selected == index,
                    onClick = {},
                )
            }
        }
    }) {

        BoxWithConstraints {
            if (it > maxWidth) {
                ScrollableTabRow(
                    modifier = modifier.fillMaxWidth(),
                    selectedTabIndex = selected,
                    indicator = {
                        Spacer(
                            modifier = Modifier
                                .tabIndicatorOffset(it[selected])
                                .padding(horizontal = 16.dp)
                                .height(3.dp)
                                .background(
                                    color = TabRowDefaults.contentColor,
                                    shape = RoundedCornerShape(100, 100)
                                )
                        )
                    },
                    divider = { Divider(Modifier.fillMaxWidth()) }
                ) {
                    tabs.forEachIndexed { index, status ->
                        Tab(
                            text = { Text(text = status.toString()) },
                            selected = selected == index,
                            onClick = { onSelectedChange(index, status) },
                            unselectedContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.fillMaxWidth((1.0 / tabs.size).toFloat())
                        )
                    }
                }
            } else {
                TabRow(
                    modifier = modifier.fillMaxWidth(),
                    selectedTabIndex = selected,
                    indicator = {
                        Spacer(
                            modifier = Modifier
                                .tabIndicatorOffset(it[selected])
                                .padding(horizontal = 16.dp)
                                .height(3.dp)
                                .background(
                                    color = TabRowDefaults.contentColor,
                                    shape = RoundedCornerShape(100, 100)
                                )
                        )
                    },
                    divider = { Divider(Modifier.fillMaxWidth()) }
                ) {
                    tabs.forEachIndexed { index, status ->
                        Tab(
                            text = { Text(text = status.toString()) },
                            selected = selected == index,
                            onClick = { onSelectedChange(index, status) },
                            unselectedContentColor = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}


@Preview
@Composable
fun TabRowPreview(darkMode: Boolean = false) {
    WatchBuddyTheme(darkTheme = darkMode) {
        Surface {
//            MediaTabRow(
//                selected = 0,
//                tabs = WatchStatus.values().toList(),
//                onSelectedChange = { _, _ -> }
//            )
        }
    }
}

@Preview
@Composable
fun TabRowPreviewDark() {
    TabRowPreview(darkMode = true)
}
