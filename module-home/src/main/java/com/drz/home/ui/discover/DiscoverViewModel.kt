package com.drz.home.ui.discover

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

sealed class DiscoverUiState {
    object Loading : DiscoverUiState()
    data class Success(val items: List<ItemBean>) : DiscoverUiState()
    data class Error(val message: String) : DiscoverUiState()
}

@HiltViewModel
class DiscoverViewModel @Inject constructor(
    private val repository: HomeRepository
) : BaseViewModel() {

    private val _state = MutableStateFlow<DiscoverUiState>(DiscoverUiState.Loading)
    val state = _state.asStateFlow()

    init { load() }

    fun load() {
        viewModelScope.launch {
            _state.value = DiscoverUiState.Loading
            when (val result = repository.getDiscover()) {
                is NetworkResult.Success -> _state.value = DiscoverUiState.Success(result.data.itemList)
                is NetworkResult.Error -> _state.value = DiscoverUiState.Error(result.message)
            }
        }
    }
}
