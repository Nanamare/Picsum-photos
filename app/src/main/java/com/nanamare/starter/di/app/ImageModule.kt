package com.nanamare.starter.di.app

import android.content.Context
import com.nanamare.starter.di.provider.ImageLoaderModule
import com.nanamare.starter.di.provider.ImageLoaderModuleImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class ImageModule {

    @Provides
    @Singleton
    fun provideImageLoaderModule(@ApplicationContext context: Context): ImageLoaderModule =
        ImageLoaderModuleImpl(context)

}