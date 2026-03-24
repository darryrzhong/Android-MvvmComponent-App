package com.drz.home.data.api

import com.drz.home.data.model.HomeResponse
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface HomeApi {

    @GET("v7/index/tab/discovery")
    suspend fun getDiscover(
        @Query("udid") udid: String = "fa53872206ed42e3857755c2756ab683fc22d64a",
        @Query("vc") vc: String = "591",
        @Query("vn") vn: String = "6.2.1"
    ): HomeResponse

    @GET("v5/index/tab/allRec")
    suspend fun getNominate(): HomeResponse

    @GET
    suspend fun getNominateMore(@Url url: String): HomeResponse

    @GET("v5/index/tab/feed")
    suspend fun getDaily(): HomeResponse

    @GET
    suspend fun getDailyMore(@Url url: String): HomeResponse
}
