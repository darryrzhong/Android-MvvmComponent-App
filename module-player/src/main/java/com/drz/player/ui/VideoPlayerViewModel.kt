package com.drz.player.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.drz.base.viewmodel.BaseViewModel
import com.drz.network.NetworkResult
import com.drz.player.data.model.RelatedData
import com.drz.player.data.model.ReplyData
import com.drz.player.data.repository.PlayerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
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
    private val savedStateHandle: SavedStateHandle,
    private val repository: PlayerRepository
) : BaseViewModel() {

    private val _state = MutableStateFlow<VideoPlayerUiState>(VideoPlayerUiState.Loading)
    val state = _state.asStateFlow()

    private val _relatedVideos = MutableStateFlow<List<RelatedData>>(emptyList())
    val relatedVideos = _relatedVideos.asStateFlow()

    private val _replies = MutableStateFlow<List<ReplyData>>(emptyList())
    val replies = _replies.asStateFlow()

    private val _repliesNextPageUrl = MutableStateFlow<String?>(null)
    private val _isLoadingMore = MutableStateFlow(false)
    val isLoadingMore = _isLoadingMore.asStateFlow()

    fun switchVideo(related: RelatedData) {
        _state.value = VideoPlayerUiState.Success(
            videoId = related.id,
            title = related.title,
            description = related.description,
            playUrl = related.playUrl,
            coverUrl = related.cover?.feed ?: "",
            blurredUrl = related.cover?.blurred ?: "",
            authorName = related.author?.name ?: "",
            authorAvatar = related.author?.icon ?: "",
            authorDesc = related.author?.description ?: "",
            collectionCount = related.consumption?.collectionCount ?: 0,
            shareCount = related.consumption?.shareCount ?: 0
        )
        _relatedVideos.value = emptyList()
        _replies.value = emptyList()
        _repliesNextPageUrl.value = null
        loadRelated(related.id)
        loadReplies(related.id)
    }

    fun loadVideo(videoId: Long) {
        val title = savedStateHandle.get<String>("title") ?: "视频详情"
        val playUrl = savedStateHandle.get<String>("playUrl") ?: ""
        val coverUrl = savedStateHandle.get<String>("coverUrl") ?: ""
        val blurredUrl = savedStateHandle.get<String>("blurredUrl") ?: ""
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
                blurredUrl = blurredUrl,
                authorName = authorName,
                authorAvatar = authorAvatar,
                authorDesc = authorDesc,
                collectionCount = 0,
                shareCount = 0
            )
            loadRelated(videoId)
            loadReplies(videoId)
        } else {
            fetchVideoDetail(videoId)
        }
    }

    private fun fetchVideoDetail(videoId: Long) {
        _state.value = VideoPlayerUiState.Loading
        viewModelScope.launch {
            when (val result = repository.getVideoDetail(videoId)) {
                is NetworkResult.Success -> {
                    val d = result.data.data
                    _state.value = VideoPlayerUiState.Success(
                        videoId = d.id,
                        title = d.title,
                        description = d.description,
                        playUrl = d.playUrl,
                        coverUrl = d.cover?.feed ?: "",
                        blurredUrl = d.cover?.blurred ?: "",
                        authorName = d.author?.name ?: "",
                        authorAvatar = d.author?.icon ?: "",
                        authorDesc = d.author?.description ?: "",
                        collectionCount = d.consumption?.collectionCount ?: 0,
                        shareCount = d.consumption?.shareCount ?: 0
                    )
                    loadRelated(videoId)
                    loadReplies(videoId)
                }
                is NetworkResult.Error -> {
                    _state.value = VideoPlayerUiState.Error(result.message)
                }
            }
        }
    }

    private fun loadRelated(videoId: Long) {
        viewModelScope.launch {
            when (val result = repository.getRelated(videoId)) {
                is NetworkResult.Success -> {
                    _relatedVideos.value = result.data.itemList
                        .filter { it.type == "videoSmallCard" }
                        .map { it.data }
                }
                is NetworkResult.Error -> {}
            }
        }
    }

    private fun loadReplies(videoId: Long) {
        viewModelScope.launch {
            when (val result = repository.getReplies(videoId)) {
                is NetworkResult.Success -> {
                    _replies.value = result.data.itemList
                        .filter { it.type == "reply" }
                        .map { it.data }
                    _repliesNextPageUrl.value = result.data.nextPageUrl
                }
                is NetworkResult.Error -> {}
            }
        }
    }

    fun loadMoreReplies() {
        val url = _repliesNextPageUrl.value ?: return
        if (_isLoadingMore.value) return
        viewModelScope.launch {
            _isLoadingMore.value = true
            when (val result = repository.getMoreReplies(url)) {
                is NetworkResult.Success -> {
                    _replies.value = _replies.value + result.data.itemList
                        .filter { it.type == "reply" }
                        .map { it.data }
                    _repliesNextPageUrl.value = result.data.nextPageUrl
                }
                is NetworkResult.Error -> {}
            }
            _isLoadingMore.value = false
        }
    }
}
