package com.drz.home

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.drz.common.navigation.AppRoutes
import com.drz.home.ui.HomeScreen
import com.drz.home.ui.daily.DailyScreen

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

fun NavGraphBuilder.dailyNavGraph(navController: NavController) {
    composable(AppRoutes.DAILY) {
        DailyScreen(
            onVideoClick = { data ->
                navController.navigate(
                    AppRoutes.videoPlayer(
                        videoId = data.id,
                        title = data.title,
                        playUrl = data.playUrl,
                        coverUrl = data.cover?.detail.orEmpty(),
                        blurredUrl = data.cover?.blurred.orEmpty(),
                        description = data.description,
                        authorName = data.author?.name.orEmpty(),
                        authorAvatar = data.author?.icon.orEmpty(),
                        authorDesc = data.author?.description.orEmpty()
                    )
                )
            }
        )
    }
}
