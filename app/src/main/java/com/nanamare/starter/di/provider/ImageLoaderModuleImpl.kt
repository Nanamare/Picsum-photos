package com.nanamare.starter.di.provider

import android.content.Context
import android.webkit.URLUtil
import coil.ImageLoader
import coil.annotation.ExperimentalCoilApi
import coil.disk.DiskCache
import coil.intercept.Interceptor
import coil.memory.MemoryCache
import coil.request.ImageResult
import coil.size.Dimension
import coil.size.Precision
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

interface ImageLoaderModule {
    fun get(): ImageLoader
}

class ImageLoaderModuleImpl @Inject constructor(@ApplicationContext val context: Context) :
    ImageLoaderModule {

    // 기본 값이 Precision.AUTOMATIC 로 allowInexactSize 가 false 면 scale 을 upScale 처리하는 로직이 있어,
    // 가로가 긴 이미지인 경우 fitCenter 처리 되면서 이미지가 매우 커지게 되어, OpenGlES 에서 Canvas: trying to draw too large 에러를 던짐
    // 관련 정보 : https://github.com/coil-kt/coil/blob/main/coil-base/src/main/java/coil/decode/BitmapFactoryDecoder.kt#L151
    @OptIn(ExperimentalCoilApi::class)
    fun getCoilImageLoaderModule(): ImageLoader = ImageLoader.Builder(context)
        .precision(Precision.INEXACT)
        .components { add(OnDemandResizingInterceptor()) }
        .memoryCache { MemoryCache.Builder(context).build() }
        .diskCache {
            DiskCache.Builder().directory(context.cacheDir.resolve("image_cache")).build()
        }
        .build()

    override fun get(): ImageLoader = getCoilImageLoaderModule()
}

@ExperimentalCoilApi
class OnDemandResizingInterceptor : Interceptor {

    override suspend fun intercept(chain: Interceptor.Chain): ImageResult {
        var request = chain.request
        when (val url = chain.request.data) {
            is String -> {
                if (URLUtil.isNetworkUrl(url)) {
                    val newImageUrl = when (val width = chain.size.width) {
                        is Dimension.Pixels -> "$url/${width.px}"
                        is Dimension.Original -> return chain.proceed(request)
                    }
                    request = chain.request.newBuilder().data(newImageUrl).build()
                }
            }
        }
        return chain.proceed(request)
    }
}