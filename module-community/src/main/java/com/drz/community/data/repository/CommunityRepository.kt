package com.drz.community.data.repository

import com.drz.community.data.api.CommunityApi
import com.drz.home.data.model.HomeResponse
import com.drz.network.NetworkResult
import com.drz.network.safeApiCall
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CommunityRepository @Inject constructor(private val api: CommunityApi) {
    suspend fun getRecommend(): NetworkResult<HomeResponse> = safeApiCall { api.getRecommend() }
    suspend fun getRecommendMore(url: String): NetworkResult<HomeResponse> = safeApiCall { api.getRecommendMore(url) }
    suspend fun getAttention(): NetworkResult<HomeResponse> = safeApiCall { api.getAttention() }
}
