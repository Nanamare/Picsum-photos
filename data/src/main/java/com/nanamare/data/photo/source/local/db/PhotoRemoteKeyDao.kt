package com.nanamare.data.photo.source.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nanamare.data.photo.model.local.PhotoRemoteKeyEntity

@Dao
interface PhotoRemoteKeyDao {

    @Query("SELECT * FROM photo_remote_key WHERE id = :id")
    suspend fun getRemoteKey(id: Long): PhotoRemoteKeyEntity?

    @Query("SELECT * FROM photo_remote_key ORDER BY lastUpdated DESC LIMIT 1")
    suspend fun getLatestRemoteKey(): PhotoRemoteKeyEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun replace(remoteKeys: List<PhotoRemoteKeyEntity>)

    @Query("DELETE FROM photo_remote_key")
    suspend fun deleteAll()

}