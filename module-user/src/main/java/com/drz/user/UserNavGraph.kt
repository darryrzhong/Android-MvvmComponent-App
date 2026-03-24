package com.drz.user

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.drz.common.navigation.AppRoutes

fun NavGraphBuilder.userNavGraph(navController: NavController) {
    composable(AppRoutes.LOGIN) {
        // LoginActivity 独立使用，此处可嵌入 Composable 登录页
        // 如需从 NavHost 跳转登录，在此注册
    }
}
