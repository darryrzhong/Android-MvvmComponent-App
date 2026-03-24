package com.drz.player.ui

import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import coil.compose.AsyncImage
import com.drz.player.data.model.RelatedData
import com.drz.player.data.model.ReplyData
import com.shuyu.gsyvideoplayer.GSYVideoManager
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun VideoPlayerScreen(
    videoId: Long,
    onBack: () -> Unit,
    viewModel: VideoPlayerViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val relatedVideos by viewModel.relatedVideos.collectAsState()
    val replies by viewModel.replies.collectAsState()
    val isLoadingMore by viewModel.isLoadingMore.collectAsState()

    LaunchedEffect(videoId) { viewModel.loadVideo(videoId) }

    BackHandler { onBack() }

    Box(modifier = Modifier.fillMaxSize()) {
        if (state is VideoPlayerUiState.Success) {
            // 背景图忽略 insets，延伸到状态栏后面
            AsyncImage(
                model = (state as VideoPlayerUiState.Success).blurredUrl,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .windowInsetsPadding(WindowInsets(0)),
                contentScale = ContentScale.Crop,
                alpha = 0.35f
            )
        }

        val listState = rememberLazyListState()

        // 滚动到底部时加载更多评论
        val shouldLoadMore by remember {
            derivedStateOf {
                val lastVisible = listState.layoutInfo.visibleItemsInfo.lastOrNull()
                val total = listState.layoutInfo.totalItemsCount
                lastVisible != null && lastVisible.index >= total - 3
            }
        }
        LaunchedEffect(shouldLoadMore) {
            if (shouldLoadMore && !isLoadingMore) viewModel.loadMoreReplies()
        }

        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxSize(),
            contentPadding = WindowInsets.navigationBars.asPaddingValues()
        ) {
            item {
                when (val s = state) {
                    is VideoPlayerUiState.Loading -> {
                        Box(
                            Modifier.fillMaxWidth().height(220.dp),
                            Alignment.Center
                        ) { CircularProgressIndicator() }
                    }
                    is VideoPlayerUiState.Success -> {
                        Column {
                            GsyVideoPlayer(
                                playUrl = s.playUrl,
                                title = s.title,
                                coverUrl = s.coverUrl
                            )
                            VideoInfoHeader(state = s, onBack = onBack)
                        }
                    }
                    is VideoPlayerUiState.Error -> {
                        Box(
                            Modifier.fillMaxWidth().height(220.dp),
                            Alignment.Center
                        ) { Text(s.message) }
                    }
                }
            }

            // 相关推荐
            if (relatedVideos.isNotEmpty()) {
                item {
                    Text(
                        text = "相关推荐",
                        style = MaterialTheme.typography.titleSmall,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                    )
                }
                items(relatedVideos) { related ->
                    RelatedVideoItem(
                        data = related,
                        onClick = { viewModel.switchVideo(related) }
                    )
                }
            }

            // 评论
            if (replies.isNotEmpty()) {
                item {
                    Text(
                        text = "评论 ${replies.size}",
                        style = MaterialTheme.typography.titleSmall,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                    )
                }
                items(replies) { reply ->
                    ReplyItem(reply = reply)
                }
                if (isLoadingMore) {
                    item {
                        Box(
                            Modifier.fillMaxWidth().padding(16.dp),
                            Alignment.Center
                        ) { CircularProgressIndicator(modifier = Modifier.size(24.dp)) }
                    }
                }
            }
        }
    }
}

@Composable
private fun GsyVideoPlayer(playUrl: String, title: String, coverUrl: String) {
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val playerRef = remember { mutableStateOf<StandardGSYVideoPlayer?>(null) }

    DisposableEffect(lifecycle) {
        val observer = LifecycleEventObserver { _, event ->
            val player = playerRef.value ?: return@LifecycleEventObserver
            when (event) {
                Lifecycle.Event.ON_PAUSE -> player.currentPlayer.onVideoPause()
                Lifecycle.Event.ON_RESUME -> player.currentPlayer.onVideoResume(false)
                Lifecycle.Event.ON_DESTROY -> GSYVideoManager.releaseAllVideos()
                else -> {}
            }
        }
        lifecycle.addObserver(observer)
        onDispose { lifecycle.removeObserver(observer) }
    }

    AndroidView(
        factory = { context ->
            StandardGSYVideoPlayer(context).apply {
                layoutParams = FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, 600
                )
                setUp(playUrl, true, title)
                thumbImageView = android.widget.ImageView(context).also { iv ->
                    iv.scaleType = android.widget.ImageView.ScaleType.CENTER_CROP
                }
                isRotateViewAuto = true
                isLockLand = false
                isAutoFullWithSize = true
                isNeedShowWifiTip = false
                startPlayLogic()
                playerRef.value = this
            }
        },
        modifier = Modifier.fillMaxWidth().height(220.dp)
    )
}

@Composable
private fun VideoInfoHeader(state: VideoPlayerUiState.Success, onBack: () -> Unit) {
    Column(modifier = Modifier.padding(16.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onBack) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "返回")
            }
            Text(
                text = state.title,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.weight(1f),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
        Spacer(Modifier.height(8.dp))
        if (state.description.isNotEmpty()) {
            Text(state.description, style = MaterialTheme.typography.bodyMedium)
            Spacer(Modifier.height(12.dp))
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (state.authorAvatar.isNotEmpty()) {
                AsyncImage(
                    model = state.authorAvatar,
                    contentDescription = state.authorName,
                    modifier = Modifier.size(36.dp).clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
                Spacer(Modifier.width(8.dp))
            }
            Column {
                if (state.authorName.isNotEmpty()) {
                    Text(state.authorName, style = MaterialTheme.typography.labelLarge)
                }
                if (state.authorDesc.isNotEmpty()) {
                    Text(
                        state.authorDesc,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
        if (state.collectionCount > 0 || state.shareCount > 0) {
            Spacer(Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                if (state.collectionCount > 0) {
                    Text(
                        "♥ ${state.collectionCount}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                if (state.shareCount > 0) {
                    Text(
                        "↗ ${state.shareCount}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@Composable
private fun RelatedVideoItem(data: RelatedData, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // 封面 + 时长角标
        Box(
            modifier = Modifier
                .width(120.dp)
                .height(72.dp)
        ) {
            AsyncImage(
                model = data.cover?.feed,
                contentDescription = data.title,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(MaterialTheme.shapes.small),
                contentScale = ContentScale.Crop
            )
            if (data.duration > 0) {
                Text(
                    text = formatDuration(data.duration),
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.White,
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(4.dp)
                        .background(Color(0x99000000), MaterialTheme.shapes.extraSmall)
                        .padding(horizontal = 4.dp, vertical = 2.dp)
                )
            }
        }
        Spacer(Modifier.width(10.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = data.title,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(Modifier.height(4.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                if (data.category.isNotEmpty()) {
                    Text(
                        text = "# ${data.category}",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                if (data.author != null) {
                    Text(
                        text = data.author.name,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

private fun formatDuration(seconds: Int): String {
    val m = seconds / 60
    val s = seconds % 60
    return "%d:%02d".format(m, s)
}

@Composable
private fun ReplyItem(reply: ReplyData) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        AsyncImage(
            model = reply.user?.avatar,
            contentDescription = reply.user?.nickname,
            modifier = Modifier.size(36.dp).clip(CircleShape),
            contentScale = ContentScale.Crop
        )
        Spacer(Modifier.width(10.dp))
        Column(modifier = Modifier.weight(1f)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = reply.user?.nickname ?: "",
                    style = MaterialTheme.typography.labelMedium
                )
                if (reply.createTime > 0) {
                    Text(
                        text = SimpleDateFormat("MM-dd", Locale.getDefault())
                            .format(Date(reply.createTime)),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            Spacer(Modifier.height(2.dp))
            Text(
                text = reply.message,
                style = MaterialTheme.typography.bodySmall
            )
            if (reply.likeCount > 0) {
                Spacer(Modifier.height(2.dp))
                Text(
                    text = "♥ ${reply.likeCount}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
    HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
}
