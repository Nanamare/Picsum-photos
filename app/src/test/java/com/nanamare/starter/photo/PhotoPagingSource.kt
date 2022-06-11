package com.nanamare.starter.photo

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.nanamare.domain.photo.model.Photo
import com.nanamare.domain.photo.usecase.GetPhotoUseCase
import javax.inject.Inject

class PhotoPagingSource @Inject constructor(private val getPhotoUseCase: GetPhotoUseCase) :
    PagingSource<Int, Photo>() {

    override fun getRefreshKey(state: PagingState<Int, Photo>): Int? =
        state.anchorPosition?.minus(1)

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Photo> {
        return try {
            val page = params.key ?: 1
            val response = getPhotoUseCase(page).getOrThrow()
            LoadResult.Page(response.photoList, response.prevPage, response.nextPage)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

}
