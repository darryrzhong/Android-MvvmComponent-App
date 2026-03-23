package com.drz.home.ui.nominate

import androidx.lifecycle.viewModelScope
import com.drz.base.viewmodel.BaseViewModel
import com.drz.home.data.model.ItemBean
import com.drz.home.data.repository.HomeRepository
import com.drz.network.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class NominateUiState {
    object Loading : NominateUiState()
    data class Success(val items: List<ItemBean>, val nextPageUrl: String?) : NominateUiState()
    data class Error(val message: String) : NominateUiState()
}

@HiltViewModel
class NominateViewModel @Inject constructor(
    private val repository: HomeRepository
) : BaseViewModel() {

    private val _state = MutableStateFlow<NominateUiState>(NominateUiState.Loading)
    val state = _state.asStateFlow()

    private val allItems = mutableListOf<ItemBean>()
    private var nextPageUrl: String? = null

    init { refresh() }

    fun refresh() {
        viewModelScope.launch {
            _state.value = NominateUiState.Loading
            allItems.clear()
            when (val result = repository.getNominate()) {
                is NetworkResult.Success -> {
                    allItems.addAll(result.data.itemList)
                    nextPageUrl = result.data.nextPageUrl
                    _state.value = NominateUiState.Success(allItems.toList(), nextPageUrl)
                }
                is NetworkResult.Error -> _state.value = NominateUiState.Error(result.message)
            }
        }
    }

    fun loadMore() {
        val url = nextPageUrl ?: return
        viewModelScope.launch {
            when (val result = repository.getNominateMore(url)) {
                is NetworkResult.Success -> {
                    allItems.addAll(result.data.itemList)
                    nextPageUrl = result.data.nextPageUrl
                    _state.value = NominateUiState.Success(allItems.toList(), nextPageUrl)
                }
                is NetworkResult.Error -> {} // 加载更多失败静默处理
            }
        }
    }
}
