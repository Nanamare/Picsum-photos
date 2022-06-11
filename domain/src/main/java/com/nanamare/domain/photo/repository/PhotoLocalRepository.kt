package com.nanamare.domain.photo.repository

import com.nanamare.domain.photo.model.Photo
import com.nanamare.domain.photo.model.PhotoRemoteKey

interface PhotoLocalRepository {
    suspend fun replacePhoto(photoEntities: List<Photo>)
    suspend fun getLatestUpdatedTime(): Long
    suspend fun deleteAll()
    suspend fun replaceRemoteKey(remoteKeys: List<PhotoRemoteKey>)
    suspend fun getRemoteKey(id: Long): PhotoRemoteKey?
    fun getAllPhoto(): List<Photo>
}
