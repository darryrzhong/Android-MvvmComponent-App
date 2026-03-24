package com.drz.community.ui

import android.annotation.SuppressLint
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.drz.base.ui.EmptyScreen
import com.drz.base.ui.ErrorScreen
import com.drz.base.ui.LoadingScreen
import com.drz.home.data.model.HeaderData
import com.drz.home.data.model.ItemBean
import com.drz.home.data.model.ItemData
import kotlinx.coroutines.delay

@Composable
fun CommunityScreen(
    onVideoClick: (Long) -> Unit,
    viewModel: CommunityViewModel = hiltViewModel()
) {
    val state by viewModel.recommendState.collectAsState()

    when (val s = state) {
        is CommunityUiState.Loading -> LoadingScreen()
        is CommunityUiState.Error -> ErrorScreen(
            message = s.message,
            onRetry = viewModel::loadRecommend
        )
        is CommunityUiState.Success -> {
            if (s.items.isEmpty()) EmptyScreen()
            else CommunityFeedList(items = s.items, onVideoClick = onVideoClick)
        }
    }
}

@Composable
private fun CommunityFeedList(items: List<ItemBean>, onVideoClick: (Long) -> Unit) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(items) { item ->
            when (item.type) {
                "horizontalScrollCard" -> HorizontalEntryCard(item = item)
                "communityColumnsCard" -> CommunityContentCard(item = item, onVideoClick = onVideoClick)
            }
        }
    }
}

// horizontalScrollCard：根据 dataType 区分横向滚动列表 or 轮播图
@Composable
private fun HorizontalEntryCard(item: ItemBean) {
    when (item.data.dataType) {
        "HorizontalScrollCard" -> HorizontalBannerCard(item = item)
        else -> HorizontalScrollListCard(item = item)  // ItemCollection 等
    }
}

// dataType=ItemCollection：横向可滚动入口列表
@Composable
private fun HorizontalScrollListCard(item: ItemBean) {
    val entries = item.data.itemList ?: return
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(entries) { entry ->
            EntryItem(
                data = entry.data,
                modifier = Modifier.width(160.dp).height(80.dp)
            )
        }
    }
}

// dataType=HorizontalScrollCard：轮播图，露出右侧下一张，3秒自动切换
@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
private fun HorizontalBannerCard(item: ItemBean) {
    val entries = item.data.itemList ?: return
    if (entries.isEmpty()) return

    val pageCount = Int.MAX_VALUE
    val initialPage = pageCount / 2 - (pageCount / 2 % entries.size)
    val pagerState = rememberPagerState(initialPage = initialPage) { pageCount }

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
        val cardWidth = maxWidth - 16.dp - 40.dp

        HorizontalPager(
            state = pagerState,
            contentPadding = PaddingValues(start = 16.dp, end = 40.dp),
            pageSpacing = 10.dp,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            EntryItem(
                data = entries[page % entries.size].data,
                modifier = Modifier.width(cardWidth).fillMaxHeight()
            )
        }

        // 圆点指示器
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val currentIndex = pagerState.currentPage % entries.size
            repeat(entries.size) { index ->
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

@Composable
private fun EntryItem(data: ItemData, modifier: Modifier = Modifier) {
    val imageUrl = data.bgPicture?.takeIf { it.isNotEmpty() }
        ?: data.image.takeIf { it.isNotEmpty() }
        ?: data.cover?.detail?.takeIf { it.isNotEmpty() }
        ?: data.cover?.feed

    Box(
        modifier = modifier.clip(MaterialTheme.shapes.large)
    ) {
        if (!imageUrl.isNullOrEmpty()) {
            AsyncImage(
                model = imageUrl,
                contentDescription = data.title,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.35f))
        )
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(10.dp)
        ) {
            Text(
                text = data.title,
                style = MaterialTheme.typography.titleSmall,
                color = Color.White,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            if (!data.subTitle.isNullOrEmpty()) {
                Text(
                    text = data.subTitle.orEmpty(),
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.White.copy(alpha = 0.8f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

// communityColumnsCard：社区内容卡片（视频 or 图片）
@Composable
private fun CommunityContentCard(item: ItemBean, onVideoClick: (Long) -> Unit) {
    val header = item.data.header ?: return
    val content = item.data.content ?: return
    val contentData = content.data

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column {
            // 头部：作者头像 + 名称
            AuthorHeader(header = header)

            // 内容区：视频 or 图片
            when (content.type) {
                "video" -> VideoContent(data = contentData, onVideoClick = onVideoClick)
                "ugcPicture" -> PictureContent(data = contentData)
            }

            // 底部：描述文字 + 互动数据
            ContentFooter(data = contentData)
        }
    }
}

@Composable
private fun AuthorHeader(header: HeaderData) {
    Row(
        modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (!header.icon.isNullOrEmpty()) {
            AsyncImage(
                model = header.icon,
                contentDescription = header.issuerName,
                modifier = Modifier.size(36.dp).clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            Spacer(Modifier.width(10.dp))
        }
        Text(
            text = header.issuerName?.ifEmpty { header.title } ?: header.title.orEmpty(),
            style = MaterialTheme.typography.titleSmall,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun VideoContent(data: ItemData, onVideoClick: (Long) -> Unit) {
    val coverUrl = data.cover?.detail ?: return
    AsyncImage(
        model = coverUrl,
        contentDescription = data.description,
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .clickable { onVideoClick(data.id) },
        contentScale = ContentScale.Crop
    )
}

@Composable
private fun PictureContent(data: ItemData) {
    val imageUrl = data.cover?.feed ?: data.urls?.firstOrNull() ?: return
    AsyncImage(
        model = imageUrl,
        contentDescription = data.description,
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp),
        contentScale = ContentScale.Crop
    )
}

@Composable
private fun ContentFooter(data: ItemData) {
    Column(modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)) {
        val desc = data.description.ifEmpty { data.title }
        if (desc.isNotEmpty()) {
            Text(
                text = desc,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(Modifier.height(6.dp))
        }
        // 互动数据
        val consumption = data.consumption
        if (consumption != null) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                if (consumption.collectionCount > 0) {
                    Text(
                        text = "♥ ${consumption.collectionCount}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                if (consumption.replyCount > 0) {
                    Text(
                        text = "💬 ${consumption.replyCount}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}
