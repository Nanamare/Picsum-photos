package com.nanamare.data.photo.source.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nanamare.data.photo.model.local.PhotoEntity
import com.nanamare.data.photo.model.local.PhotoRemoteKeyEntity

@Database(entities = [PhotoEntity::class, PhotoRemoteKeyEntity::class], version = 1, exportSchema = false)
abstract class PhotoDatabase : RoomDatabase() {
    abstract fun getPhotoDao(): PhotoDao
    abstract fun getPhotoRemoteKeyDao(): PhotoRemoteKeyDao
}