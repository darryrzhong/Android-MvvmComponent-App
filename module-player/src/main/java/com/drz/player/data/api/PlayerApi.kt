package com.drz.player.data.api

import com.drz.player.data.model.RelatedResponse
import com.drz.player.data.model.ReplyResponse
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface PlayerApi {

    @GET("v4/video/related")
    suspend fun getRelated(@Query("id") videoId: Long): RelatedResponse

    @GET("v2/replies/video")
    suspend fun getReplies(
        @Query("videoId") videoId: Long,
        @Query("num") num: Int = 20
    ): ReplyResponse

    @GET
    suspend fun getMoreReplies(@Url url: String): ReplyResponse
}
