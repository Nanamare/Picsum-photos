package com.nanamare.data.photo.model

import android.net.Uri
import com.nanamare.data.photo.model.local.PhotoEntity
import com.nanamare.data.photo.model.local.PhotoRemoteKeyEntity
import com.nanamare.data.photo.model.remote.PhotoDto
import com.nanamare.domain.photo.model.Photo
import com.nanamare.domain.photo.model.PhotoRemoteKey

fun PhotoDto.toDomainModel() =
    Photo(author, downloadUrl, Uri.EMPTY.toString(), height, id, url, width)

fun Photo.toEntity() = PhotoEntity(
    author = author,
    downloadUrl = downloadUrl,
    imageUrl = imageUrl,
    height = height,
    id = id,
    url = url,
    width = width
)

fun PhotoEntity.toUiModel() = Photo(author, downloadUrl, imageUrl, height, id, url, width)

fun PhotoEntity.toDomainModel() = Photo(author, downloadUrl, imageUrl, height, id, url, width)

fun PhotoRemoteKeyEntity.toDomainModel() = PhotoRemoteKey(id, prevPage, nextPage, lastUpdated)

fun PhotoRemoteKey.toDto() = PhotoRemoteKeyEntity(id, prevPage, nextPage, lastUpdated)