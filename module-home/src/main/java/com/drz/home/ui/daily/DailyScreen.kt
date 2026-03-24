package com.drz.home.ui.daily

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.drz.base.ui.EmptyScreen
import com.drz.base.ui.ErrorScreen
import com.drz.base.ui.LoadingScreen
import com.drz.home.data.model.ItemBean
import com.drz.home.data.model.ItemData

@Composable
fun DailyScreen(
    onVideoClick: (ItemData) -> Unit,
    viewModel: DailyViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    when (val s = state) {
        is DailyUiState.Loading -> LoadingScreen()
        is DailyUiState.Error -> ErrorScreen(message = s.message, onRetry = viewModel::refresh)
        is DailyUiState.Success -> {
            if (s.items.isEmpty()) EmptyScreen()
            else DailyList(items = s.items, hasMore = s.nextPageUrl != null,
                onVideoClick = onVideoClick, onLoadMore = viewModel::loadMore)
        }
    }
}

@Composable
private fun DailyList(
    items: List<ItemBean>,
    hasMore: Boolean,
    onVideoClick: (ItemData) -> Unit,
    onLoadMore: () -> Unit
) {
    val listState = rememberLazyListState()
    val reachedBottom by remember {
        derivedStateOf {
            val last = listState.layoutInfo.visibleItemsInfo.lastOrNull()
            last != null && last.index >= listState.layoutInfo.totalItemsCount - 3
        }
    }
    LaunchedEffect(reachedBottom) { if (reachedBottom && hasMore) onLoadMore() }

    LazyColumn(state = listState, modifier = Modifier.fillMaxSize()) {
        items(items) { item ->
            when (item.type) {
                "videoSmallCard" -> DailyVideoItem(item = item, onVideoClick = onVideoClick)
                "followCard" -> item.data.content?.let { content ->
                    FollowCard(header = item.data.header, content = content, onVideoClick = onVideoClick)
                }
            }
        }
    }
}

@Composable
private fun FollowCard(
    header: com.drz.home.data.model.HeaderData?,
    content: ItemBean,
    onVideoClick: (ItemData) -> Unit
) {
    val videoData = content.data
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .clickable { onVideoClick(videoData) },
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column {
            // 头部：作者头像 + 名称 + 描述
            if (header != null) {
                Row(
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (!header.icon.isNullOrEmpty()) {
                        AsyncImage(
                            model = header.icon,
                            contentDescription = header.title,
                            modifier = Modifier.size(36.dp).clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )
                        Spacer(Modifier.width(10.dp))
                    }
                    Column {
                        Text(
                            text = header.title.orEmpty(),
                            style = MaterialTheme.typography.titleSmall,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        if (!header.description.isNullOrEmpty()) {
                            Text(
                                text = header.description,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                }
            }
            // 封面图
            AsyncImage(
                model = videoData.cover?.detail,
                contentDescription = videoData.title,
                modifier = Modifier.fillMaxWidth().height(200.dp),
                contentScale = ContentScale.Crop
            )
            // 视频标题 + 描述
            Column(modifier = Modifier.padding(12.dp)) {
                Text(
                    text = videoData.title,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                if (videoData.description.isNotEmpty()) {
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = videoData.description,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}

@Composable
private fun DailyVideoItem(item: ItemBean, onVideoClick: (ItemData) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .clickable { onVideoClick(item.data) },
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column {
            AsyncImage(
                model = item.data.cover?.detail,
                contentDescription = item.data.title,
                modifier = Modifier.fillMaxWidth().height(200.dp),
                contentScale = ContentScale.Crop
            )
            Column(modifier = Modifier.padding(12.dp)) {
                Text(
                    text = item.data.title,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = item.data.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}
