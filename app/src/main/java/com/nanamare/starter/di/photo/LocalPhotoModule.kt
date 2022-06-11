package com.nanamare.starter.di.photo

import com.nanamare.domain.photo.repository.PhotoLocalRepository
import com.nanamare.data.photo.repository.PhotoLocalRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class LocalPhotoModule {

    @Singleton
    @Provides
    fun provideLocalPhotoRepository(photoLocalRepositoryImpl: PhotoLocalRepositoryImpl): PhotoLocalRepository =
        photoLocalRepositoryImpl

}