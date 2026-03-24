package com.drz.common.config

object ModuleLifecycleReflexs {
    private const val BaseInit = "com.drz.common.CommonModuleInit"
    private const val MainInit = "com.drz.main.application.MainModuleInit"
    private const val UserInit = "com.drz.user.UserModuleInit"
    private const val HomeInit = "com.drz.home.HomeModuleInit"
    private const val CommunityInit = "com.drz.community.CommunityModuleInit"
    private const val MoreInit = "com.drz.more.MoreModuleInit"
    private const val PlayerInit = "com.drz.player.PlayerModuleInit"

    val initModuleNames = arrayOf(
        BaseInit, MainInit, UserInit, HomeInit, CommunityInit, MoreInit, PlayerInit
    )
}
