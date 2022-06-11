package com.nanamare.data.photo.source.local.db

import android.annotation.SuppressLint
import android.database.Cursor
import androidx.room.RoomSQLiteQuery
import androidx.room.paging.LimitOffsetPagingSource
import androidx.room.util.CursorUtil
import com.nanamare.data.photo.model.local.PhotoEntity
import com.nanamare.data.photo.model.toDomainModel
import com.nanamare.domain.photo.model.Photo
import javax.inject.Inject

// Generate Code Copy & Paste
@SuppressLint("RestrictedApi")
class LocalPhotoPagingSource @Inject constructor(photoDatabase: PhotoDatabase) :
    LimitOffsetPagingSource<Photo>(
        RoomSQLiteQuery.acquire("SELECT * FROM photo", 0),
        photoDatabase,
        "photo"
    ) {

    override fun convertRows(cursor: Cursor): List<Photo> {
        val _cursorIndexOfPrimaryKey = CursorUtil.getColumnIndexOrThrow(cursor, "primaryKey")
        val _cursorIndexOfAuthor = CursorUtil.getColumnIndexOrThrow(cursor, "author")
        val _cursorIndexOfDownloadUrl = CursorUtil.getColumnIndexOrThrow(cursor, "downloadUrl")
        val _cursorIndexOfImageUrl = CursorUtil.getColumnIndexOrThrow(cursor, "imageUrl")
        val _cursorIndexOfHeight = CursorUtil.getColumnIndexOrThrow(cursor, "height")
        val _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(cursor, "id")
        val _cursorIndexOfUrl = CursorUtil.getColumnIndexOrThrow(cursor, "url")
        val _cursorIndexOfWidth = CursorUtil.getColumnIndexOrThrow(cursor, "width")
        val _result: MutableList<PhotoEntity> = ArrayList(cursor.count)
        while (cursor.moveToNext()) {
            val _item: PhotoEntity
            val _tmpPrimaryKey: Long = cursor.getLong(_cursorIndexOfPrimaryKey)
            val _tmpAuthor: String? = if (cursor.isNull(_cursorIndexOfAuthor)) {
                null
            } else {
                cursor.getString(_cursorIndexOfAuthor)
            }
            val _tmpDownloadUrl: String? = if (cursor.isNull(_cursorIndexOfDownloadUrl)) {
                null
            } else {
                cursor.getString(_cursorIndexOfDownloadUrl)
            }
            val _tmpImageUrl: String? = if (cursor.isNull(_cursorIndexOfImageUrl)) {
                null
            } else {
                cursor.getString(_cursorIndexOfImageUrl)
            }
            val _tmpHeight: Int? = if (cursor.isNull(_cursorIndexOfHeight)) {
                null
            } else {
                cursor.getInt(_cursorIndexOfHeight)
            }
            val _tmpId: Long = cursor.getLong(_cursorIndexOfId)
            val _tmpUrl: String? = if (cursor.isNull(_cursorIndexOfUrl)) {
                null
            } else {
                cursor.getString(_cursorIndexOfUrl)
            }
            val _tmpWidth: Int? = if (cursor.isNull(_cursorIndexOfWidth)) {
                null
            } else {
                cursor.getInt(_cursorIndexOfWidth)
            }
            _item = PhotoEntity(
                _tmpPrimaryKey,
                _tmpAuthor,
                _tmpDownloadUrl,
                _tmpImageUrl,
                _tmpHeight,
                _tmpId,
                _tmpUrl,
                _tmpWidth
            )
            _result.add(_item)
        }
        return _result.map(PhotoEntity::toDomainModel).toList()
    }

}