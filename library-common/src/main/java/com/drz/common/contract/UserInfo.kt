package com.drz.common.contract

data class UserInfo(
    val uuid: String = "",
    val name: String = "",
    val email: String = "",
    val phone: String = "",
    val avatarUrl: String = "",
    val signature: String = "",
    val accessToken: String = "",
    val refreshToken: String = ""
)
