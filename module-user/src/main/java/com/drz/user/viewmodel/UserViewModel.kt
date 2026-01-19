package com.drz.user.viewmodel

import androidx.lifecycle.viewModelScope
import com.drz.base.viewmodel.BaseViewModel
import com.drz.user.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val repository: UserRepository
) : BaseViewModel() {

    private val _loginState = MutableStateFlow<LoginUiState>(LoginUiState.Idle)
    val loginState = _loginState.asStateFlow()

    fun login(username: String, password: String) {
        launchDataLoad {
            repository.login(username, password).collect { result ->
                result.onSuccess {
                    _loginState.value = LoginUiState.Success(it)
                }.onFailure {
                    _loginState.value = LoginUiState.Error(it.message ?: "Login failed")
                }
            }
        }
    }
}

sealed class LoginUiState {
    object Idle : LoginUiState()
    data class Success(val msg: String) : LoginUiState()
    data class Error(val msg: String) : LoginUiState()
}
