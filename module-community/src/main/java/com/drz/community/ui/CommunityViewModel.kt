package com.drz.community.ui

import androidx.lifecycle.viewModelScope
import com.drz.base.viewmodel.BaseViewModel
import com.drz.community.data.repository.CommunityRepository
import com.drz.home.data.model.ItemBean
import com.drz.network.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class CommunityUiState {
    object Loading : CommunityUiState()
    data class Success(val items: List<ItemBean>) : CommunityUiState()
    data class Error(val message: String) : CommunityUiState()
}

@HiltViewModel
class CommunityViewModel @Inject constructor(
    private val repository: CommunityRepository
) : BaseViewModel() {

    private val _recommendState = MutableStateFlow<CommunityUiState>(CommunityUiState.Loading)
    val recommendState = _recommendState.asStateFlow()

    private val _nextPageUrl = MutableStateFlow<String?>(null)
    private val _isLoadingMore = MutableStateFlow(false)
    val isLoadingMore = _isLoadingMore.asStateFlow()
    val hasMore get() = _nextPageUrl.value != null

    init {
        loadRecommend()
    }

    fun loadRecommend() = viewModelScope.launch {
        _recommendState.value = CommunityUiState.Loading
        _nextPageUrl.value = null
        when (val r = repository.getRecommend()) {
            is NetworkResult.Success -> {
                _recommendState.value = CommunityUiState.Success(r.data.itemList)
                _nextPageUrl.value = r.data.nextPageUrl
            }
            is NetworkResult.Error -> _recommendState.value = CommunityUiState.Error(r.message)
        }
    }

    fun loadMore() {
        val url = _nextPageUrl.value ?: return
        if (_isLoadingMore.value) return
        viewModelScope.launch {
            _isLoadingMore.value = true
            when (val r = repository.getRecommendMore(url)) {
                is NetworkResult.Success -> {
                    val current = (_recommendState.value as? CommunityUiState.Success)?.items ?: emptyList()
                    _recommendState.value = CommunityUiState.Success(current + r.data.itemList)
                    _nextPageUrl.value = r.data.nextPageUrl
                }
                is NetworkResult.Error -> {}
            }
            _isLoadingMore.value = false
        }
    }
}
