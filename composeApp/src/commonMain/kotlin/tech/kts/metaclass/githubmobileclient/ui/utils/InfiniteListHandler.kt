package tech.kts.metaclass.githubmobileclient.ui.utils

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import kotlinx.coroutines.flow.filter

@Composable
fun InfiniteListHandler(
    listState: LazyListState,
    buffer: Int = 2,
    onLoadMore: () -> Unit
) {
    LaunchedEffect(listState) {
        snapshotFlow {
            val layoutInfo = listState.layoutInfo
            val lastItemIndex = layoutInfo.totalItemsCount - 1
            val lastVisibleItemIndex = layoutInfo.visibleItemsInfo.lastOrNull()?.index

            lastVisibleItemIndex != null && lastVisibleItemIndex >= (lastItemIndex - buffer)
        }
            .filter { it }
            .collect {
                onLoadMore()
            }
    }
}