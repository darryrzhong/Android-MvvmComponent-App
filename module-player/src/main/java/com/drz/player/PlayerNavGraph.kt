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
        arguments = listOf(
            navArgument("videoId") { type = NavType.LongType },
            navArgument("title") { type = NavType.StringType; defaultValue = "" },
            navArgument("playUrl") { type = NavType.StringType; defaultValue = "" },
            navArgument("coverUrl") { type = NavType.StringType; defaultValue = "" },
            navArgument("blurredUrl") { type = NavType.StringType; defaultValue = "" },
            navArgument("description") { type = NavType.StringType; defaultValue = "" },
            navArgument("authorName") { type = NavType.StringType; defaultValue = "" },
            navArgument("authorAvatar") { type = NavType.StringType; defaultValue = "" },
            navArgument("authorDesc") { type = NavType.StringType; defaultValue = "" }
        )
    ) { backStackEntry ->
        val videoId = backStackEntry.arguments?.getLong("videoId") ?: 0L
        VideoPlayerScreen(
            videoId = videoId,
            onBack = { navController.popBackStack() }
        )
    }
}
