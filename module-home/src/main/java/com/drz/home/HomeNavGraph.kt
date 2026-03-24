package com.drz.home

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.drz.common.navigation.AppRoutes
import com.drz.home.ui.HomeScreen

fun NavGraphBuilder.homeNavGraph(navController: NavController) {
    composable(AppRoutes.HOME) {
        HomeScreen(
            onVideoClick = { videoId ->
                navController.navigate(AppRoutes.videoPlayer(videoId))
            },
            onUrlClick = { url ->
                navController.navigate(AppRoutes.webView(url))
            }
        )
    }
}
