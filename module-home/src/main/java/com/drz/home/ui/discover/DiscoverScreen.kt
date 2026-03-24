package com.drz.home.ui.discover

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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

@Composable
fun DiscoverScreen(
    onVideoClick: (Long) -> Unit,
    viewModel: DiscoverViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    when (val s = state) {
        is DiscoverUiState.Loading -> LoadingScreen()
        is DiscoverUiState.Error -> ErrorScreen(message = s.message, onRetry = viewModel::load)
        is DiscoverUiState.Success -> {
            if (s.items.isEmpty()) {
                EmptyScreen()
            } else {
                DiscoverList(items = s.items, onVideoClick = onVideoClick)
            }
        }
    }
}

@Composable
private fun DiscoverList(items: List<ItemBean>, onVideoClick: (Long) -> Unit) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = 8.dp)
    ) {
        items(items) { item ->
            DiscoverItem(item = item, onVideoClick = onVideoClick)
        }
    }
}

@Composable
private fun DiscoverItem(item: ItemBean, onVideoClick: (Long) -> Unit) {
    when (item.type) {
        "videoSmallCard" -> VideoCard(item = item, onVideoClick = onVideoClick)
        "textCard" -> TextCard(text = item.data.text)
        "banner" -> BannerCard(imageUrl = item.data.image.ifEmpty { item.data.cover?.detail ?: "" })
        "horizontalScrollCard" -> HorizontalBannerCard(item = item)
        "briefCard" -> BriefCard(item = item)
        "columnCardList" -> ColumnCardList(item = item, onVideoClick = onVideoClick)
        "squareCardOfColumn" -> SquareColumnCard(item = item)
        else -> {} // 忽略未处理的卡片类型
    }
}

@Composable
private fun VideoCard(item: ItemBean, onVideoClick: (Long) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .clickable { onVideoClick(item.data.id) },
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
                    text = "${item.data.author?.name ?: ""} / #${item.data.category}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
private fun TextCard(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleSmall,
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
    )
}

@Composable
private fun BannerCard(imageUrl: String) {
    if (imageUrl.isNotEmpty()) {
        AsyncImage(
            model = imageUrl,
            contentDescription = null,
            modifier = Modifier.fillMaxWidth().height(180.dp),
            contentScale = ContentScale.Crop
        )
    }
}

// horizontalScrollCard：横向滚动 Banner 列表（banner2 子卡片）
@Composable
private fun HorizontalBannerCard(item: ItemBean) {
    val banners = item.data.itemList ?: return
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(banners) { banner ->
            val imageUrl = banner.data.image.ifEmpty { banner.data.cover?.detail ?: "" }
            if (imageUrl.isNotEmpty()) {
                AsyncImage(
                    model = imageUrl,
                    contentDescription = banner.data.title,
                    modifier = Modifier
                        .width(280.dp)
                        .height(160.dp)
                        .clip(MaterialTheme.shapes.medium),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}

// squareCardOfColumn：开眼栏目子卡片（图片 + 标题）
@Composable
private fun SquareColumnCard(item: ItemBean) {
    val imageUrl = item.data.image.ifEmpty { item.data.cover?.feed ?: item.data.cover?.detail ?: "" }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp)
    ) {
        if (imageUrl.isNotEmpty()) {
            AsyncImage(
                model = imageUrl,
                contentDescription = item.data.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .clip(MaterialTheme.shapes.medium),
                contentScale = ContentScale.Crop
            )
        }
        if (item.data.title.isNotEmpty()) {
            Text(
                text = item.data.title,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 4.dp),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

// briefCard：话题/分类卡片（icon + 标题 + 描述）
@Composable
private fun BriefCard(item: ItemBean) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = item.data.icon,
            contentDescription = item.data.title,
            modifier = Modifier
                .size(48.dp)
                .clip(MaterialTheme.shapes.small),
            contentScale = ContentScale.Crop
        )
        Spacer(Modifier.width(12.dp))
        Column {
            Text(
                text = item.data.title,
                style = MaterialTheme.typography.titleSmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            if (item.data.description.isNotEmpty()) {
                Text(
                    text = item.data.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

// columnCardList：嵌套视频列表，展开其中的子卡片
@Composable
private fun ColumnCardList(item: ItemBean, onVideoClick: (Long) -> Unit) {
    Column {
        item.data.header?.let { header ->
            if (header.title.isNotEmpty()) {
                Text(
                    text = header.title,
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }
        }
        item.data.itemList?.forEach { child ->
            DiscoverItem(item = child, onVideoClick = onVideoClick)
        }
    }
}
