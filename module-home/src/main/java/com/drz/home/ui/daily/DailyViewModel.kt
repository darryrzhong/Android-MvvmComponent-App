package com.drz.home.ui.daily

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

sealed class DailyUiState {
    object Loading : DailyUiState()
    data class Success(val items: List<ItemBean>, val nextPageUrl: String?) : DailyUiState()
    data class Error(val message: String) : DailyUiState()
}

@HiltViewModel
class DailyViewModel @Inject constructor(
    private val repository: HomeRepository
) : BaseViewModel() {

    private val _state = MutableStateFlow<DailyUiState>(DailyUiState.Loading)
    val state = _state.asStateFlow()

    private val allItems = mutableListOf<ItemBean>()
    private var nextPageUrl: String? = null

    init { refresh() }

    fun refresh() {
        viewModelScope.launch {
            _state.value = DailyUiState.Loading
            allItems.clear()
            when (val result = repository.getDaily()) {
                is NetworkResult.Success -> {
                    allItems.addAll(result.data.itemList)
                    nextPageUrl = result.data.nextPageUrl
                    _state.value = DailyUiState.Success(allItems.toList(), nextPageUrl)
                }
                is NetworkResult.Error -> _state.value = DailyUiState.Error(result.message)
            }
        }
    }

    fun loadMore() {
        val url = nextPageUrl ?: return
        viewModelScope.launch {
            when (val result = repository.getDailyMore(url)) {
                is NetworkResult.Success -> {
                    allItems.addAll(result.data.itemList)
                    nextPageUrl = result.data.nextPageUrl
                    _state.value = DailyUiState.Success(allItems.toList(), nextPageUrl)
                }
                is NetworkResult.Error -> {}
            }
        }
    }
}
