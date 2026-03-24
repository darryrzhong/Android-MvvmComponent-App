package com.drz.home.data.model

import com.google.gson.annotations.SerializedName

data class HomeResponse(
    val itemList: List<ItemBean> = emptyList(),
    val nextPageUrl: String? = null
)

data class ItemBean(
    val type: String = "",
    val data: ItemData = ItemData()
)

data class ItemData(
    val id: Long = 0,
    val type: String = "",       // textCard 的子类型：header5、footer2、header7 等
    val actionUrl: String = "",  // 点击跳转链接
    val title: String = "",
    val description: String = "",
    val category: String = "",
    val image: String = "",
    @SerializedName("playUrl") val playUrl: String = "",
    val duration: Int = 0,
    val cover: CoverData? = null,
    val author: AuthorData? = null,
    val consumption: ConsumptionData? = null,
    val header: HeaderData? = null,
    val itemList: List<ItemBean>? = null,
    val text: String = "",
    @SerializedName("rightText") val rightText: String = "",
    val icon: String = "",
    val content: ItemBean? = null,
    val urls: List<String>? = null,
    val subTitle: String? = null,
    val bgPicture: String? = null,
    val dataType: String? = null,
)

data class CoverData(
    val detail: String = "",
    val blurred: String = "",
    val feed: String = ""
)

data class AuthorData(
    val id: Long = 0,
    val name: String = "",
    val description: String = "",
    val icon: String = ""
)

data class ConsumptionData(
    val collectionCount: Int = 0,
    val shareCount: Int = 0,
    val replyCount: Int = 0
)

data class HeaderData(
    val title: String? = null,
    val rightText: String? = null,
    val icon: String? = null,
    val description: String? = null,
    val actionUrl: String? = null,
    val issuerName: String? = null
)
