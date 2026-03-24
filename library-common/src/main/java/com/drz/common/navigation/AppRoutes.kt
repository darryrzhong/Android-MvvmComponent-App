package com.drz.common.navigation

object AppRoutes {
    // 底部导航 Tab
    const val HOME = "home"
    const val COMMUNITY = "community"
    const val DAILY = "daily"

    // 详情页
    const val VIDEO_PLAYER = "video_player/{videoId}?title={title}&playUrl={playUrl}&coverUrl={coverUrl}&blurredUrl={blurredUrl}&description={description}&authorName={authorName}&authorAvatar={authorAvatar}&authorDesc={authorDesc}"
    const val LOGIN = "login"
    const val WEB_VIEW = "web_view/{url}"

    fun videoPlayer(
        videoId: Long,
        title: String = "",
        playUrl: String = "",
        coverUrl: String = "",
        blurredUrl: String = "",
        description: String = "",
        authorName: String = "",
        authorAvatar: String = "",
        authorDesc: String = ""
    ): String {
        val encode = { s: String -> java.net.URLEncoder.encode(s, "UTF-8") }
        return "video_player/$videoId?title=${encode(title)}&playUrl=${encode(playUrl)}&coverUrl=${encode(coverUrl)}&blurredUrl=${encode(blurredUrl)}&description=${encode(description)}&authorName=${encode(authorName)}&authorAvatar=${encode(authorAvatar)}&authorDesc=${encode(authorDesc)}"
    }

    fun webView(url: String) = "web_view/${java.net.URLEncoder.encode(url, "UTF-8")}"
}
