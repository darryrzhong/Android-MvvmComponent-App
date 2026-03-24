package com.drz.player.data.repository

import com.drz.network.NetworkResult
import com.drz.network.safeApiCall
import com.drz.player.data.api.PlayerApi
import com.drz.player.data.model.RelatedResponse
import com.drz.player.data.model.ReplyResponse
import com.drz.player.data.model.VideoDetailResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PlayerRepository @Inject constructor(private val api: PlayerApi) {

    suspend fun getVideoDetail(videoId: Long): NetworkResult<VideoDetailResponse> =
        safeApiCall { api.getVideoDetail(videoId) }

    suspend fun getRelated(videoId: Long): NetworkResult<RelatedResponse> =
        safeApiCall { api.getRelated(videoId) }

    suspend fun getReplies(videoId: Long): NetworkResult<ReplyResponse> =
        safeApiCall { api.getReplies(videoId) }

    suspend fun getMoreReplies(url: String): NetworkResult<ReplyResponse> =
        safeApiCall { api.getMoreReplies(url) }
}
