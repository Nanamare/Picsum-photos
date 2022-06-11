package com.nanamare.data.photo.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "photo_remote_key")
data class PhotoRemoteKeyEntity(
    @PrimaryKey
    val id: Long,
    val prevPage: Int?,
    val nextPage: Int?,
    val lastUpdated: Long
)