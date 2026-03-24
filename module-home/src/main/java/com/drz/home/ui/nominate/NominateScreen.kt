package com.drz.home.ui.nominate

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.drz.base.ui.EmptyScreen
import com.drz.base.ui.ErrorScreen
import com.drz.base.ui.LoadingScreen
import com.drz.home.data.model.ItemBean

@Composable
fun NominateScreen(
    onVideoClick: (Long) -> Unit,
    viewModel: NominateViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    when (val s = state) {
        is NominateUiState.Loading -> LoadingScreen()
        is NominateUiState.Error -> ErrorScreen(message = s.message, onRetry = viewModel::refresh)
        is NominateUiState.Success -> {
            if (s.items.isEmpty()) {
                EmptyScreen()
            } else {
                NominateList(
                    items = s.items,
                    hasMore = s.nextPageUrl != null,
                    onVideoClick = onVideoClick,
                    onLoadMore = viewModel::loadMore
                )
            }
        }
    }
}

@Composable
private fun NominateList(
    items: List<ItemBean>,
    hasMore: Boolean,
    onVideoClick: (Long) -> Unit,
    onLoadMore: () -> Unit
) {
    val listState = rememberLazyListState()
    val reachedBottom by remember {
        derivedStateOf {
            val lastVisibleItem = listState.layoutInfo.visibleItemsInfo.lastOrNull()
            lastVisibleItem != null && lastVisibleItem.index >= listState.layoutInfo.totalItemsCount - 3
        }
    }
    LaunchedEffect(reachedBottom) {
        if (reachedBottom && hasMore) onLoadMore()
    }

    LazyColumn(state = listState, modifier = Modifier.fillMaxSize()) {
        items(items) { item ->
            when (item.type) {
                "videoSmallCard", "followCard" -> VideoListItem(item = item, onVideoClick = onVideoClick)
                "squareCardCollection" -> item.data.itemList?.forEach { child ->
                    VideoListItem(item = child, onVideoClick = onVideoClick)
                }
                "textCard" -> Text(
                    text = item.data.text,
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
                )
                else -> {}
            }
        }
    }
}

@Composable
private fun VideoListItem(item: ItemBean, onVideoClick: (Long) -> Unit) {
    val data = if (item.type == "followCard") item.data.content?.data ?: item.data else item.data
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onVideoClick(data.id) }
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        AsyncImage(
            model = data.cover?.detail,
            contentDescription = data.title,
            modifier = Modifier.size(120.dp, 80.dp),
            contentScale = ContentScale.Crop
        )
        Spacer(Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = data.title,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = data.author?.name ?: "",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
    HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
}
