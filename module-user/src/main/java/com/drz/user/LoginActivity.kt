package com.drz.user

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.alibaba.android.arouter.facade.annotation.Route
import com.drz.common.router.RouterActivityPath
import com.drz.user.viewmodel.LoginUiState
import com.drz.user.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * 登录页面 - 已重构为 Compose + Hilt + MVVM
 * @author darryrzhoong
 */
@Route(path = RouterActivityPath.User.PAGER_LOGIN)
@AndroidEntryPoint
class LoginActivity : ComponentActivity() {

    private val viewModel: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoginScreen(viewModel)
        }
    }
}

@Composable
fun LoginScreen(viewModel: UserViewModel) {
    val uiState by viewModel.loginState.collectAsStateWithLifecycle()
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current

    LaunchedEffect(uiState) {
        when (uiState) {
            is LoginUiState.Success -> {
                Toast.makeText(context, (uiState as LoginUiState.Success).msg, Toast.LENGTH_SHORT).show()
                // Navigate to main or finish
            }
            is LoginUiState.Error -> {
                Toast.makeText(context, (uiState as LoginUiState.Error).msg, Toast.LENGTH_SHORT).show()
            }
            else -> {}
        }
    }

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Username") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { viewModel.login(username, password) },
                enabled = !isLoading,
                modifier = Modifier.fillMaxWidth()
            ) {
                if (isLoading) {
                    CircularProgressIndicator(modifier = Modifier.size(24.dp))
                } else {
                    Text("Login")
                }
            }
        }
    }
}
