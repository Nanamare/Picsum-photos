package com.nanamare.starter.ui.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.nanamare.data.photo.source.local.db.LocalPhotoPagingSource
import com.nanamare.data.photo.source.local.db.PhotoRemoteMediator
import com.nanamare.domain.photo.model.Photo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Provider

@OptIn(ExperimentalPagingApi::class)
class PhotoPagingManager @Inject constructor(
    @Named("page_count") private val pageCount: Provider<Int>,
    private val photoRemoteMediator: PhotoRemoteMediator,
    private val localPhotoPagingSource: Provider<LocalPhotoPagingSource> // Pager 가 불릴 때 마다 항상 만들어져야함
) {
    // SpanSize 의 배수로 요청하지 않으면, footer more load 에서 심미적으로 그려지지 않음.
    operator fun invoke(): Flow<PagingData<Photo>> = Pager(
        PagingConfig(pageSize = pageCount.get()),
        pagingSourceFactory = { localPhotoPagingSource.get() },
        remoteMediator = photoRemoteMediator
    ).flow

}