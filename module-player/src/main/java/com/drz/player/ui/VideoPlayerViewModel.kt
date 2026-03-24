package com.drz.player.ui

import androidx.lifecycle.SavedStateHandle
import com.drz.base.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

sealed class VideoPlayerUiState {
    object Loading : VideoPlayerUiState()
    data class Success(
        val videoId: Long,
        val title: String,
        val description: String,
        val playUrl: String,
        val coverUrl: String,
        val blurredUrl: String,
        val authorName: String,
        val authorAvatar: String,
        val authorDesc: String,
        val collectionCount: Int,
        val shareCount: Int
    ) : VideoPlayerUiState()
    data class Error(val message: String) : VideoPlayerUiState()
}

@HiltViewModel
class VideoPlayerViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    private val _state = MutableStateFlow<VideoPlayerUiState>(VideoPlayerUiState.Loading)
    val state = _state.asStateFlow()

    fun loadVideo(videoId: Long) {
        // 从 savedStateHandle 获取视频详情
        // 实际项目中应通过 videoId 调用 API 获取详情
        // 这里展示通过 savedStateHandle 传递的数据恢复
        val title = savedStateHandle.get<String>("title") ?: "视频详情"
        val playUrl = savedStateHandle.get<String>("playUrl") ?: ""
        val coverUrl = savedStateHandle.get<String>("coverUrl") ?: ""
        val description = savedStateHandle.get<String>("description") ?: ""
        val authorName = savedStateHandle.get<String>("authorName") ?: ""
        val authorAvatar = savedStateHandle.get<String>("authorAvatar") ?: ""
        val authorDesc = savedStateHandle.get<String>("authorDesc") ?: ""

        if (playUrl.isNotEmpty()) {
            _state.value = VideoPlayerUiState.Success(
                videoId = videoId,
                title = title,
                description = description,
                playUrl = playUrl,
                coverUrl = coverUrl,
                blurredUrl = "",
                authorName = authorName,
                authorAvatar = authorAvatar,
                authorDesc = authorDesc,
                collectionCount = 0,
                shareCount = 0
            )
        } else {
            _state.value = VideoPlayerUiState.Error("视频信息不完整，请返回重试")
        }
    }
}
