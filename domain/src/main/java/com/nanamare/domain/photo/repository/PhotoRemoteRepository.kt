package com.nanamare.domain.photo.repository

import com.nanamare.domain.photo.model.PhotoPagingModel

interface PhotoRemoteRepository {
    suspend fun getPhoto(page: Int): PhotoPagingModel
}