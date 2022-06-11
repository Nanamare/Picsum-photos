package com.nanamare.data.photo.source.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nanamare.data.photo.model.local.PhotoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PhotoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun replace(photos: List<PhotoEntity>)

    @Query("SELECT * FROM photo")
    fun getAllPhotos(): List<PhotoEntity>

    @Query("SELECT * FROM photo WHERE id = :id")
    fun getPhoto(id: Long): Flow<PhotoEntity>

    @Query("DELETE FROM photo")
    suspend fun deleteAll()

}