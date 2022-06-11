package com.nanamare.data.photo.source.local.db

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.nanamare.data.photo.model.local.PhotoRemoteKeyEntity
import com.nanamare.data.photo.model.toDomainModel
import com.nanamare.domain.photo.model.Photo
import com.nanamare.domain.photo.model.PhotoRemoteKey
import com.nanamare.domain.photo.repository.PhotoLocalRepository
import com.nanamare.domain.photo.usecase.GetPhotoUseCase
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * RemoteMediator 에서 Local, Remote 에 대한 접근을 모두 하기 때문에, PhotoRemoteRepository, PhotoLocalRepository 를 가지고 있음
 * 마치 DataSource 와 같은 역할을 함
 */
@OptIn(ExperimentalPagingApi::class)
class PhotoRemoteMediator @Inject constructor(
    private val photoDatabase: PhotoDatabase,
    private val getPhotoUseCase: GetPhotoUseCase, // PhotoRemoteRepository
    private val localRepository: PhotoLocalRepository,
) : RemoteMediator<Int, Photo>() {

    private val cacheTimeOut by lazy { TimeUnit.MILLISECONDS.convert(7, TimeUnit.DAYS) }

    override suspend fun initialize(): InitializeAction =
        if (System.currentTimeMillis() - localRepository.getLatestUpdatedTime() >= cacheTimeOut) {
            InitializeAction.SKIP_INITIAL_REFRESH
        } else {
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Photo>
    ): MediatorResult {
        try {
            val localPage = when (loadType) {
                LoadType.REFRESH -> { // 로드
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextPage?.minus(1) ?: 1
                }
                LoadType.PREPEND -> { // 이전
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    val prevPage = remoteKeys?.prevPage
                        ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                    prevPage
                }
                LoadType.APPEND -> { // 이후
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextPage = remoteKeys?.nextPage
                        ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                    nextPage
                }
            }

            val response = getPhotoUseCase.invoke(localPage).getOrThrow()

            photoDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    localRepository.deleteAll()
                }

                val remoteKey = response.photoList.map { photo ->
                    PhotoRemoteKeyEntity(
                        id = photo.id,
                        prevPage = response.prevPage,
                        nextPage = response.nextPage,
                        lastUpdated = System.currentTimeMillis()
                    )
                }

                localRepository.replaceRemoteKey(remoteKey.map(PhotoRemoteKeyEntity::toDomainModel))
                localRepository.replacePhoto(response.photoList)
            }

            return MediatorResult.Success(endOfPaginationReached = response.photoList.isEmpty())
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, Photo>,
    ): PhotoRemoteKey? = state.anchorPosition?.let { position ->
        state.closestItemToPosition(position)?.id?.let { id ->
            localRepository.getRemoteKey(id)
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, Photo>,
    ): PhotoRemoteKey? =
        state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?.id?.let { id ->
            localRepository.getRemoteKey(id)
        }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, Photo>,
    ): PhotoRemoteKey? =
        state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.id?.let { id ->
            localRepository.getRemoteKey(id)
        }

}