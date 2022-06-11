package com.nanamare.starter

import android.app.Application
import coil.Coil
import com.nanamare.starter.di.provider.ImageLoaderModule
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

/**
 * 해당 프로젝트는 안드로이드 개발을 쉽게 하기 위한 스타트업팩 입니다 by Nanamare
 */
@HiltAndroidApp
class App : Application() {

    @Inject
    lateinit var imageLoaderModule: ImageLoaderModule

    override fun onCreate() {
        super.onCreate()
        Coil.setImageLoader(imageLoaderModule.get())
    }

}