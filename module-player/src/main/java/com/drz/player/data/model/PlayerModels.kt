package com.drz.player.data.model

import com.google.gson.annotations.SerializedName

// 相关推荐接口响应
data class RelatedResponse(
    val itemList: List<RelatedItem> = emptyList(),
    val nextPageUrl: String? = null
)

data class RelatedItem(
    val type: String = "",
    val data: RelatedData = RelatedData()
)

data class RelatedData(
    val id: Long = 0,
    val title: String = "",
    val description: String = "",
    val category: String = "",
    @SerializedName("playUrl") val playUrl: String = "",
    val duration: Int = 0,
    val cover: RelatedCover? = null,
    val author: RelatedAuthor? = null,
    val consumption: RelatedConsumption? = null
)

data class RelatedCover(
    val detail: String = "",
    val blurred: String = "",
    val feed: String = ""
)

data class RelatedAuthor(
    val id: Long = 0,
    val name: String = "",
    val description: String = "",
    val icon: String = ""
)

data class RelatedConsumption(
    val collectionCount: Int = 0,
    val shareCount: Int = 0,
    val replyCount: Int = 0
)

// 评论接口响应
data class ReplyResponse(
    val itemList: List<ReplyItem> = emptyList(),
    val nextPageUrl: String? = null,
    val total: Int = 0
)

data class ReplyItem(
    val type: String = "",
    val data: ReplyData = ReplyData()
)

data class ReplyData(
    val id: Long = 0,
    val content: String = "",
    val likeCount: Int = 0,
    val createTime: Long = 0,
    val user: ReplyUser? = null
)

data class ReplyUser(
    val uid: Long = 0,
    val nickname: String = "",
    val avatar: String = "",
    val description: String = ""
)
