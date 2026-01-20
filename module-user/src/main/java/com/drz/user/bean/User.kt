package com.drz.user.bean

data class User(
    val id: Long,
    val username: String,
    val nickname: String,
    val icon: String?,
    val email: String?,
    val token: String?
)
