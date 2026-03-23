package com.drz.home.data.repository

import com.drz.home.data.api.HomeApi
import com.drz.home.data.model.HomeResponse
import com.drz.network.NetworkResult
import com.drz.network.safeApiCall
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomeRepository @Inject constructor(private val api: HomeApi) {

    suspend fun getDiscover(): NetworkResult<HomeResponse> = safeApiCall { api.getDiscover() }

    suspend fun getNominate(): NetworkResult<HomeResponse> = safeApiCall { api.getNominate() }

    suspend fun getNominateMore(url: String): NetworkResult<HomeResponse> =
        safeApiCall { api.getNominateMore(url) }

    suspend fun getDaily(): NetworkResult<HomeResponse> = safeApiCall { api.getDaily() }

    suspend fun getDailyMore(url: String): NetworkResult<HomeResponse> =
        safeApiCall { api.getDailyMore(url) }
}
