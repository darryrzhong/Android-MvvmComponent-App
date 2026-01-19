package com.drz.base.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * 现代化的 BaseViewModel，基于 Kotlin Coroutines 和 Flow
 */
open class BaseViewModel : ViewModel() {

    // 统一的加载状态流
    protected val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    // 统一的错误信息流
    protected val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage = _errorMessage.asStateFlow()

    /**
     * 启动一个协程任务，并自动处理 Loading 状态和异常
     */
    protected fun launchDataLoad(
        onError: ((Exception) -> Unit)? = null,
        block: suspend CoroutineScope.() -> Unit
    ) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                block()
            } catch (e: Exception) {
                e.printStackTrace()
                _errorMessage.value = e.message ?: "Unknown Error"
                onError?.invoke(e)
            } finally {
                _isLoading.value = false
            }
        }
    }
}
