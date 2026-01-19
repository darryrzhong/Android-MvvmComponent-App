package com.drz.user.api

import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * 用户相关 API 接口
 */
interface UserApi {

    @FormUrlEncoded
    @POST("user/login")
    suspend fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ): Any // 实际项目中应定义 BaseResponse<User>
}
