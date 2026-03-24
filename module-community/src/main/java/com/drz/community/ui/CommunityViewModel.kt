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

    private val _attentionState = MutableStateFlow<CommunityUiState>(CommunityUiState.Loading)
    val attentionState = _attentionState.asStateFlow()

    init {
        loadRecommend()
        loadAttention()
    }

    fun loadRecommend() = viewModelScope.launch {
        _recommendState.value = CommunityUiState.Loading
        _recommendState.value = when (val r = repository.getRecommend()) {
            is NetworkResult.Success -> CommunityUiState.Success(r.data.itemList)
            is NetworkResult.Error -> CommunityUiState.Error(r.message)
        }
    }

    fun loadAttention() = viewModelScope.launch {
        _attentionState.value = CommunityUiState.Loading
        _attentionState.value = when (val r = repository.getAttention()) {
            is NetworkResult.Success -> CommunityUiState.Success(r.data.itemList)
            is NetworkResult.Error -> CommunityUiState.Error(r.message)
        }
    }
}
