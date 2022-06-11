package com.nanamare.data.photo.repository

import com.nanamare.data.photo.model.local.PhotoEntity
import com.nanamare.data.photo.model.toDomainModel
import com.nanamare.data.photo.model.toDto
import com.nanamare.data.photo.model.toEntity
import com.nanamare.data.photo.source.local.db.PhotoDao
import com.nanamare.data.photo.source.local.db.PhotoRemoteKeyDao
import com.nanamare.domain.photo.model.Photo
import com.nanamare.domain.photo.model.PhotoRemoteKey
import com.nanamare.domain.photo.repository.PhotoLocalRepository
import javax.inject.Inject

class PhotoLocalRepositoryImpl @Inject constructor(
    private val photoDao: PhotoDao,
    private val photoRemoteKeyDao: PhotoRemoteKeyDao
) : PhotoLocalRepository {

    override fun getAllPhoto(): List<Photo> =
        photoDao.getAllPhotos().map(PhotoEntity::toDomainModel)

    // 비어 있으면 현재 시간으로 가져와서 새로 가져오도록 처리
    override suspend fun getLatestUpdatedTime(): Long =
        photoRemoteKeyDao.getLatestRemoteKey()?.lastUpdated ?: System.currentTimeMillis()

    override suspend fun deleteAll() {
        photoRemoteKeyDao.deleteAll()
        photoDao.deleteAll()
    }

    override suspend fun replacePhoto(photoEntities: List<Photo>) {
        photoDao.replace(photoEntities.map(Photo::toEntity))
    }

    override suspend fun replaceRemoteKey(remoteKeys: List<PhotoRemoteKey>) {
        photoRemoteKeyDao.replace(remoteKeys.map(PhotoRemoteKey::toDto))
    }

    override suspend fun getRemoteKey(id: Long): PhotoRemoteKey? =
        photoRemoteKeyDao.getRemoteKey(id)?.toDomainModel()

}