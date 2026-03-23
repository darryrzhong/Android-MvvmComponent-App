package com.drz.common.navigation

object AppRoutes {
    // 底部导航 Tab
    const val HOME = "home"
    const val COMMUNITY = "community"
    const val MORE = "more"

    // 详情页
    const val VIDEO_PLAYER = "video_player/{videoId}"
    const val LOGIN = "login"

    fun videoPlayer(videoId: Long) = "video_player/$videoId"
}
