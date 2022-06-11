package com.nanamare.data.photo.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "photo")
data class PhotoEntity(
    @PrimaryKey(autoGenerate = true)
    val primaryKey: Long = 0,
    val author: String? = "",
    val downloadUrl: String? = "",
    var imageUrl: String? = "",
    val height: Int? = 0,
    val id: Long,
    val url: String? = "",
    val width: Int? = 0
)