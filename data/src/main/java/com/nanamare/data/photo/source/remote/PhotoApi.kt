package com.nanamare.data.photo.source.remote

import com.nanamare.data.photo.model.remote.PhotoDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PhotoApi {

    @GET("/v2/list")
    fun getPhotoList(
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): Call<List<PhotoDto>>

}