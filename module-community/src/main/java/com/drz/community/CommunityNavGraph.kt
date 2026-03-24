package com.drz.community

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.drz.common.navigation.AppRoutes
import com.drz.community.ui.CommunityScreen

fun NavGraphBuilder.communityNavGraph(navController: NavController) {
    composable(AppRoutes.COMMUNITY) {
        CommunityScreen(onVideoClick = { data ->
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
        })
    }
}
