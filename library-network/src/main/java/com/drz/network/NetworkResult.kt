package com.drz.network

import retrofit2.HttpException
import java.io.IOException

sealed class NetworkResult<out T> {
    data class Success<T>(val data: T) : NetworkResult<T>()
    data class Error(val code: Int, val message: String) : NetworkResult<Nothing>()
}

suspend fun <T> safeApiCall(call: suspend () -> T): NetworkResult<T> = try {
    NetworkResult.Success(call())
} catch (e: HttpException) {
    NetworkResult.Error(e.code(), e.message() ?: "HTTP 错误 ${e.code()}")
} catch (e: IOException) {
    NetworkResult.Error(-1, e.message ?: "网络连接失败")
} catch (e: Exception) {
    NetworkResult.Error(-2, e.message ?: "未知错误")
}
