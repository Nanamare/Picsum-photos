package com.nanamare.domain.photo.model


data class PhotoPagingModel(
    val currentPage: Int,
    val nextPage: Int?,
    val prevPage: Int?,
    val photoList: List<Photo>,
)

data class Photo(
    val author: String?,
    val downloadUrl: String?,
    var imageUrl: String?,
    val height: Int?,
    val id: Long,
    val url: String?,
    val width: Int?
)

data class PhotoRemoteKey(
    val id: Long,
    val prevPage: Int?,
    val nextPage: Int?,
    val lastUpdated: Long
)