package com.drz.player.ui

import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import coil.compose.AsyncImage
import com.shuyu.gsyvideoplayer.GSYVideoManager
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer

@Composable
fun VideoPlayerScreen(
    videoId: Long,
    onBack: () -> Unit,
    viewModel: VideoPlayerViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(videoId) { viewModel.loadVideo(videoId) }

    BackHandler { onBack() }

    Box(modifier = Modifier.fillMaxSize()) {
        // 模糊背景图
        if (state is VideoPlayerUiState.Success) {
            AsyncImage(
                model = (state as VideoPlayerUiState.Success).blurredUrl,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                alpha = 0.35f
            )
        }

        Column(modifier = Modifier.fillMaxSize()) {
            when (val s = state) {
                is VideoPlayerUiState.Loading -> {
                    Box(Modifier.fillMaxWidth().height(220.dp), Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
                is VideoPlayerUiState.Success -> {
                    GsyVideoPlayer(
                        playUrl = s.playUrl,
                        title = s.title,
                        coverUrl = s.coverUrl
                    )
                    VideoDetails(state = s, onBack = onBack)
                }
                is VideoPlayerUiState.Error -> {
                    Box(Modifier.fillMaxWidth().height(220.dp), Alignment.Center) {
                        Text(s.message)
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
private fun VideoDetails(state: VideoPlayerUiState.Success, onBack: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onBack) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "返回")
            }
            Text(
                text = state.title,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.weight(1f)
            )
        }
        Spacer(Modifier.height(8.dp))
        Text(state.description, style = MaterialTheme.typography.bodyMedium)
        Spacer(Modifier.height(16.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(
                model = state.authorAvatar,
                contentDescription = state.authorName,
                modifier = Modifier.size(40.dp)
            )
            Spacer(Modifier.width(8.dp))
            Column {
                Text(state.authorName, style = MaterialTheme.typography.labelLarge)
                Text(state.authorDesc, style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
    }
}
