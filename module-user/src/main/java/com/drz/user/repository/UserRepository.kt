package com.drz.user.repository

import com.drz.user.api.UserApi
import com.drz.user.bean.User
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
    
    suspend fun login(username: String, password: String): Flow<Result<User>> = flow {
        try {
            val response = userApi.login(username, password)
            if (response.isOk) {
                // response.data could be null if parsing failed or data is null
                if (response.data != null) {
                    emit(Result.success(response.data))
                } else {
                    emit(Result.failure(Exception("Login failed: empty data")))
                }
            } else {
                emit(Result.failure(Exception(response.msg ?: "Unknown error")))
            }
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }
}
