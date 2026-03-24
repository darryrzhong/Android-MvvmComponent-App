package com.drz.user.repository

import com.drz.network.NetworkResult
import com.drz.network.safeApiCall
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

    fun login(username: String, password: String): Flow<Result<User>> = flow {
        when (val result = safeApiCall { userApi.login(username, password) }) {
            is NetworkResult.Success -> emit(Result.success(result.data))
            is NetworkResult.Error -> emit(Result.failure(Exception(result.message)))
        }
    }
}
