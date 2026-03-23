package com.drz.base.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.drz.network.NetworkResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

open class BaseViewModel : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage = _errorMessage.asStateFlow()

    fun clearError() {
        _errorMessage.value = null
    }

    protected fun <T> launchRequest(
        onSuccess: (T) -> Unit,
        onError: ((String) -> Unit)? = null,
        block: suspend () -> NetworkResult<T>
    ) {
        viewModelScope.launch {
            _isLoading.value = true
            when (val result = block()) {
                is NetworkResult.Success -> {
                    _isLoading.value = false
                    onSuccess(result.data)
                }
                is NetworkResult.Error -> {
                    _isLoading.value = false
                    val msg = result.message
                    _errorMessage.value = msg
                    onError?.invoke(msg)
                }
            }
        }
    }

    protected fun launchDataLoad(block: suspend () -> Unit) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                block()
            } catch (e: Exception) {
                _errorMessage.value = e.message ?: "未知错误"
            } finally {
                _isLoading.value = false
            }
        }
    }
}
