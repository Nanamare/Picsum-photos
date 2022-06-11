package com.nanamare.starter.di.photo

import com.nanamare.data.photo.repository.PhotoRemoteRepositoryImpl
import com.nanamare.data.photo.source.remote.PhotoApi
import com.nanamare.domain.photo.repository.PhotoRemoteRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Named
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RemotePhotoModule {

    @Singleton
    @Provides
    fun providePhotoApi(@Named("retrofit") retrofit: Retrofit): PhotoApi = retrofit.create()

    @InstallIn(SingletonComponent::class)
    @Module
    interface Binder {

        @Singleton
        @Binds
        fun bindRepository(repository: PhotoRemoteRepositoryImpl): PhotoRemoteRepository
    }

}