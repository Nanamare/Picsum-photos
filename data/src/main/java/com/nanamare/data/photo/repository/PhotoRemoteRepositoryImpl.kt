package com.nanamare.data.photo.repository

import android.net.Uri
import com.nanamare.data.BuildConfig
import com.nanamare.data.photo.model.remote.PhotoDto
import com.nanamare.data.photo.model.toDomainModel
import com.nanamare.data.photo.source.remote.PhotoApi
import com.nanamare.domain.photo.model.Photo
import com.nanamare.domain.photo.model.PhotoPagingModel
import com.nanamare.domain.photo.repository.PhotoRemoteRepository
import retrofit2.Response
import retrofit2.awaitResponse
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Provider

class PhotoRemoteRepositoryImpl @Inject constructor(
    private val photoApi: PhotoApi,
    @Named("page_count") private val pageCount: Provider<Int>,
) : PhotoRemoteRepository {

    override suspend fun getPhoto(page: Int): PhotoPagingModel {
        val response = photoApi.getPhotoList(page, pageCount.get()).awaitResponse()
        val photoList = response.body().orEmpty().map(PhotoDto::toDomainModel).onEach {
            it.imageUrl = getImageUrl(it)
        }
        val nextPage = getNextPage(response, page)
        val prevPage = getPrevPage(response, page)
        return PhotoPagingModel(
            currentPage = page,
            nextPage = nextPage,
            prevPage = prevPage,
            photoList = photoList
        )
    }

    private fun getPrevPage(response: Response<List<PhotoDto>>, page: Int): Int? =
        if (response.findPrevPage()) {
            page - 1
        } else {
            null
        }

    private fun getNextPage(response: Response<List<PhotoDto>>, page: Int) =
        if (response.findNextPage()) {
            page + 1
        } else {
            null
        }

    private fun getImageUrl(it: Photo) =
        Uri.parse(BuildConfig.BASE_URL)
            .buildUpon()
            .appendPath(IMAGE_ID_PATH)
            .appendPath(it.id.toString())
            .build().toString()

    private fun Response<*>.findNextPage(): Boolean =
        headers()[PAGE_HEADER_NAME]?.contains(NEXT_PAGE_NAME) ?: false

    private fun Response<*>.findPrevPage(): Boolean =
        headers()[PAGE_HEADER_NAME]?.contains(PREV_PAGE_NAME) ?: false

    companion object {
        private const val PAGE_HEADER_NAME = "Link"
        private const val NEXT_PAGE_NAME = "next"
        private const val PREV_PAGE_NAME = "prev"
        private const val IMAGE_ID_PATH = "id"
    }

}