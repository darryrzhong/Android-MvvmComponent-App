package com.drz.home.ui.discover

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.drz.base.ui.EmptyScreen
import com.drz.base.ui.ErrorScreen
import com.drz.base.ui.LoadingScreen
import com.drz.home.data.model.ItemBean
import java.net.URLDecoder

/** eyepetizer://webview/?url=<encoded> 中提取真实 http URL */
private fun parseWebViewUrl(actionUrl: String): String? {
    if (!actionUrl.startsWith("eyepetizer://webview/")) return null
    return try {
        val query = actionUrl.substringAfter("?")
        val encoded = query.split("&").firstOrNull { it.startsWith("url=") }
            ?.removePrefix("url=") ?: return null
        URLDecoder.decode(encoded, "UTF-8")
    } catch (e: Exception) {
        null
    }
}

@Composable
fun DiscoverScreen(
    onVideoClick: (Long) -> Unit,
    onUrlClick: (String) -> Unit = {},
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
                DiscoverList(items = s.items, onVideoClick = onVideoClick, onUrlClick = onUrlClick)
            }
        }
    }
}

@Composable
private fun DiscoverList(items: List<ItemBean>, onVideoClick: (Long) -> Unit, onUrlClick: (String) -> Unit) {
    val merged = remember(items) { mergeTextCards(items) }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = 8.dp)
    ) {
        items(merged) { item ->
            DiscoverItem(item = item, onVideoClick = onVideoClick, onUrlClick = onUrlClick)
        }
    }
}

/**
 * footer2 与其所属区块的标题 textCard 不一定相邻（中间可能隔着 briefCard）。
 * 策略：向前找最近的非 footer2 的 textCard，将 footer2 的 text/actionUrl 合并进去，
 * 然后把 footer2 本身从列表中移除。
 */
private fun mergeTextCards(items: List<ItemBean>): List<ItemBean> {
    val result = items.toMutableList()
    val footerIndices = result.indices.filter {
        result[it].type == "textCard" && result[it].data.type == "footer2"
    }
    // 从后往前处理，避免 index 偏移
    for (fi in footerIndices.reversed()) {
        val footer = result[fi]
        // 向前找最近的标题 textCard
        val headerIdx = (fi - 1 downTo 0).firstOrNull {
            result[it].type == "textCard" && result[it].data.type != "footer2"
        } ?: continue
        // 合并 rightText / actionUrl
        result[headerIdx] = result[headerIdx].copy(
            data = result[headerIdx].data.copy(
                rightText = footer.data.text,
                actionUrl = footer.data.actionUrl
            )
        )
        result.removeAt(fi)
    }
    return result
}

@Composable
private fun DiscoverItem(item: ItemBean, onVideoClick: (Long) -> Unit, onUrlClick: (String) -> Unit) {
    when (item.type) {
        "videoSmallCard" -> VideoCard(item = item, onVideoClick = onVideoClick)
        "textCard" -> TextCard(item = item, onUrlClick = onUrlClick)
        "banner" -> BannerCard(imageUrl = item.data.image.ifEmpty { item.data.cover?.detail ?: "" }, actionUrl = item.data.actionUrl, onUrlClick = onUrlClick)
        "horizontalScrollCard" -> HorizontalBannerCard(item = item, onUrlClick = onUrlClick)
        "briefCard" -> BriefCard(item = item, onUrlClick = onUrlClick)
        "columnCardList" -> ColumnCardList(item = item, onVideoClick = onVideoClick, onUrlClick = onUrlClick)
        "squareCardOfColumn" -> SquareColumnCard(item = item, onUrlClick = onUrlClick)
        else -> {}
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
private fun TextCard(item: ItemBean, onUrlClick: (String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = item.data.text,
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.weight(1f)
        )
        if (item.data.rightText.isNotEmpty()) {
            val webUrl = parseWebViewUrl(item.data.actionUrl)
            Text(
                text = item.data.rightText,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary,
                modifier = if (webUrl != null) Modifier.clickable { onUrlClick(webUrl) } else Modifier
            )
        }
    }
}

@Composable
private fun BannerCard(imageUrl: String, actionUrl: String, onUrlClick: (String) -> Unit) {
    if (imageUrl.isNotEmpty()) {
        val webUrl = parseWebViewUrl(actionUrl)
        AsyncImage(
            model = imageUrl,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .then(if (webUrl != null) Modifier.clickable { onUrlClick(webUrl) } else Modifier),
            contentScale = ContentScale.Crop
        )
    }
}

// horizontalScrollCard：圆角卡片轮播 Banner，露出右侧下一张，3秒自动切换
@Composable
private fun HorizontalBannerCard(item: ItemBean, onUrlClick: (String) -> Unit) {
    val banners = item.data.itemList ?: return
    if (banners.isEmpty()) return

    val pageCount = Int.MAX_VALUE
    val initialPage = pageCount / 2 - (pageCount / 2 % banners.size)
    val pagerState = rememberPagerState(initialPage = initialPage) { pageCount }

    // 自动轮播，用 spring 动画让切换更顺滑
    LaunchedEffect(pagerState) {
        while (true) {
            delay(3000)
            pagerState.animateScrollToPage(
                page = pagerState.currentPage + 1,
                animationSpec = tween(durationMillis = 600)
            )
        }
    }

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
    ) {
        // 卡片宽度 = 全宽 - 左边距16 - 右侧露出部分40，让右侧下一张可见
        val cardWidth = maxWidth - 16.dp - 40.dp

        HorizontalPager(
            state = pagerState,
            contentPadding = PaddingValues(start = 16.dp, end = 40.dp),
            pageSpacing = 10.dp,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            val banner = banners[page % banners.size]
            val imageUrl = banner.data.image.ifEmpty { banner.data.cover?.detail ?: "" }
            val webUrl = parseWebViewUrl(banner.data.actionUrl)
            AsyncImage(
                model = imageUrl,
                contentDescription = banner.data.title,
                modifier = Modifier
                    .width(cardWidth)
                    .fillMaxHeight()
                    .clip(MaterialTheme.shapes.large)
                    .then(if (webUrl != null) Modifier.clickable { onUrlClick(webUrl) } else Modifier),
                contentScale = ContentScale.Crop
            )
        }

        // 圆点指示器
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val currentIndex = pagerState.currentPage % banners.size
            repeat(banners.size) { index ->
                Box(
                    modifier = Modifier
                        .size(if (index == currentIndex) 8.dp else 6.dp)
                        .clip(CircleShape)
                        .background(
                            if (index == currentIndex) Color.White
                            else Color.White.copy(alpha = 0.5f)
                        )
                )
            }
        }
    }
}

// squareCardOfColumn：开眼栏目子卡片，圆角卡片 + 图片上叠加描述
@Composable
private fun SquareColumnCard(item: ItemBean, onUrlClick: (String) -> Unit) {
    val imageUrl = item.data.image.ifEmpty { item.data.cover?.feed ?: item.data.cover?.detail ?: "" }
    val webUrl = parseWebViewUrl(item.data.actionUrl)
    if (imageUrl.isEmpty()) return
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .height(180.dp)
            .clip(MaterialTheme.shapes.large)
            .then(if (webUrl != null) Modifier.clickable { onUrlClick(webUrl) } else Modifier)
    ) {
        AsyncImage(
            model = imageUrl,
            contentDescription = item.data.title,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomStart)
                .background(Color.Black.copy(alpha = 0.4f))
                .padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
            if (item.data.title.isNotEmpty()) {
                Text(
                    text = item.data.title,
                    style = MaterialTheme.typography.titleSmall,
                    color = Color.White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            if (item.data.description.isNotEmpty()) {
                Text(
                    text = item.data.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.White.copy(alpha = 0.75f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

// briefCard：话题/分类卡片（icon + 标题 + 描述）
@Composable
private fun BriefCard(item: ItemBean, onUrlClick: (String) -> Unit) {
    val webUrl = parseWebViewUrl(item.data.actionUrl)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .then(if (webUrl != null) Modifier.clickable { onUrlClick(webUrl) } else Modifier),
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
private fun ColumnCardList(item: ItemBean, onVideoClick: (Long) -> Unit, onUrlClick: (String) -> Unit) {
    Column {
        item.data.header?.let { header ->
            if (!header.title.isNullOrEmpty()) {
                Text(
                    text = header.title,
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }
        }
        item.data.itemList?.forEach { child ->
            DiscoverItem(item = child, onVideoClick = onVideoClick, onUrlClick = onUrlClick)
        }
    }
}
