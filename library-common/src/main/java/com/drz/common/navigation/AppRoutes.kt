package com.drz.common.navigation

object AppRoutes {
    // 底部导航 Tab
    const val HOME = "home"
    const val COMMUNITY = "community"
    const val MORE = "more"

    // 详情页
    const val VIDEO_PLAYER = "video_player/{videoId}"
    const val LOGIN = "login"
    const val WEB_VIEW = "web_view/{url}"

    fun videoPlayer(videoId: Long) = "video_player/$videoId"
    fun webView(url: String) = "web_view/${java.net.URLEncoder.encode(url, "UTF-8")}"
}
