package com.nanamare.starter.di.app

import android.content.Context
import androidx.room.Room
import com.nanamare.data.photo.source.local.db.PhotoDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun providePhotoDatabase(@ApplicationContext context: Context): PhotoDatabase =
        Room.databaseBuilder(context, PhotoDatabase::class.java, PHOTO_TABLE_NAME)
            .fallbackToDestructiveMigration()
            .build()

    @Singleton
    @Provides
    fun providePhotoDao(database: PhotoDatabase) = database.getPhotoDao()

    @Singleton
    @Provides
    fun providePhotoRemoteKeyDao(database: PhotoDatabase) = database.getPhotoRemoteKeyDao()

    companion object {
        private const val PHOTO_TABLE_NAME = "picsum_photo.db"
    }

}