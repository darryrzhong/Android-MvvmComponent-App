package com.drz.community

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.drz.common.navigation.AppRoutes
import com.drz.community.ui.CommunityScreen

fun NavGraphBuilder.communityNavGraph(navController: NavController) {
    composable(AppRoutes.COMMUNITY) {
        CommunityScreen(onVideoClick = { videoId ->
            navController.navigate(AppRoutes.videoPlayer(videoId))
        })
    }
}
