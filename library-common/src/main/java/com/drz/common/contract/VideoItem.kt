package com.drz.common.contract

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class VideoItem(
    val id: Long = 0,
    val title: String = "",
    val description: String = "",
    val category: String = "",
    val playerUrl: String = "",
    val blurredUrl: String = "",
    val coverUrl: String = "",
    val duration: Int = 0,
    val collectionCount: Int = 0,
    val shareCount: Int = 0,
    val authorName: String = "",
    val authorAvatar: String = "",
    val authorDescription: String = ""
) : Parcelable
