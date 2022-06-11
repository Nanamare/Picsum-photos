package com.nanamare.starter.di.app

import android.content.Context
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.nanamare.starter.BuildConfig
import com.nanamare.starter.util.NetworkConnection
import com.nanamare.starter.util.NetworkConnectionImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.Cache
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Provides
    fun provideHttpLoggingInterceptor() = HttpLoggingInterceptor().apply {
        level = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
    }

    @Provides
    fun provideHttpClientBuilder() = OkHttpClient.Builder()
        .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
        .readTimeout(TIME_OUT, TimeUnit.SECONDS)
        .writeTimeout(TIME_OUT, TimeUnit.SECONDS)

    @Provides
    @Named("kotlinx")
    fun provideKotlinxSerializationJson() = Json {
        isLenient = false
        ignoreUnknownKeys = true
        coerceInputValues = true
    }

    @OptIn(ExperimentalSerializationApi::class)
    @Named("kotlinxConverter")
    @Provides
    fun provideConverterFactory(@Named("kotlinx") json: Json): Converter.Factory =
        json.asConverterFactory("application/json".toMediaType())

    @Singleton
    @Provides
    @Named("retrofit")
    fun provideRetrofit(
        httpClient: OkHttpClient.Builder,
        httpLoggingInterceptor: HttpLoggingInterceptor,
        @Named("kotlinxConverter") converter: Converter.Factory,
        @ApplicationContext context: Context
    ): Retrofit {

        if (BuildConfig.DEBUG) {
            httpClient.addInterceptor(httpLoggingInterceptor)
        }

        val cache = Cache(context.cacheDir, (5 * 1024 * 1024).toLong()) // 5MB

        val client = httpClient.cache(cache).build()

        return Retrofit.Builder()
            .baseUrl(com.nanamare.data.BuildConfig.BASE_URL)
            .client(client)
            .addConverterFactory(converter)
            .build()
    }

    @Singleton
    @Provides
    fun provideNetworkConnection(@ApplicationContext context: Context): NetworkConnection =
        NetworkConnectionImpl(context)

    companion object {
        private const val TIME_OUT = 30L
    }
}