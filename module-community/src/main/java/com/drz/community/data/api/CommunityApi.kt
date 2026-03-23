package com.drz.community.data.api

import com.drz.home.data.model.HomeResponse
import retrofit2.http.GET
import retrofit2.http.Url

interface CommunityApi {
    @GET("v7/community/tab/rec")
    suspend fun getRecommend(): HomeResponse

    @GET
    suspend fun getRecommendMore(@Url url: String): HomeResponse

    @GET("v7/community/tab/follow")
    suspend fun getAttention(): HomeResponse
}
