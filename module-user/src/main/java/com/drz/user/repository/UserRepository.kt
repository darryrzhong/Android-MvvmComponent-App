package com.drz.user.repository

import com.drz.user.api.UserApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * 用户数据仓库
 * 负责从网络或本地获取数据
 */
class UserRepository @Inject constructor(
    private val userApi: UserApi
) {
    
    suspend fun login(username: String, password: String): Flow<Result<String>> = flow {
        // 模拟网络请求，实际应调用 userApi.login
        // val response = userApi.login(username, password)
        // 这里为了演示直接返回成功
        kotlinx.coroutines.delay(1000) // 模拟耗时
        emit(Result.success("Login Success: $username"))
    }
}
