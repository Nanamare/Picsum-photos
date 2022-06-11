package com.nanamare.data.photo.model.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PhotoDto(
    @SerialName("author")
    val author: String = "",
    @SerialName("download_url")
    val downloadUrl: String = "",
    @SerialName("height")
    val height: Int = 0,
    @SerialName("id")
    val id: Long = 0,
    @SerialName("url")
    val url: String = "",
    @SerialName("width")
    val width: Int = 0
)