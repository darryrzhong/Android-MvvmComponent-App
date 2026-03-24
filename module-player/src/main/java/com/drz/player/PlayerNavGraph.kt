package com.drz.player

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.drz.common.navigation.AppRoutes
import com.drz.player.ui.VideoPlayerScreen

fun NavGraphBuilder.playerNavGraph(navController: NavController) {
    composable(
        route = AppRoutes.VIDEO_PLAYER,
        arguments = listOf(navArgument("videoId") { type = NavType.LongType })
    ) { backStackEntry ->
        val videoId = backStackEntry.arguments?.getLong("videoId") ?: 0L
        VideoPlayerScreen(
            videoId = videoId,
            onBack = { navController.popBackStack() }
        )
    }
}
