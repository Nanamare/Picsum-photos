package com.nanamare.starter.photo

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingSource
import com.nanamare.domain.photo.model.PhotoPagingModel
import com.nanamare.domain.photo.repository.PhotoRemoteRepository
import com.nanamare.domain.photo.usecase.GetPhotoUseCase
import com.nanamare.starter.common.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Rule
import org.junit.Test
import org.hamcrest.Matchers.equalTo as isEqualTo

@OptIn(ExperimentalCoroutinesApi::class)
class PhotoViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutineRule = MainCoroutineRule()

    @Test
    fun `PhotoUseCase 테스트`() = runTest {
        // given
        val photoUseCase =
            GetPhotoUseCase(FakePhotoRemoteRepository(), coroutineRule.testDispatcher)

        // when
        val actual = photoUseCase(1).getOrThrow()

        // then
        assertThat(actual.photoList, isEqualTo(MockData.photoList))
    }

    @Test
    fun `PagingSource NextKey 에 2가 들어있는지 테스트`() = runTest {
        // given
        val pagingSource = PhotoPagingSource(
            GetPhotoUseCase(
                FakePhotoRemoteRepository(),
                coroutineRule.testDispatcher
            )
        )

        // when
        val actual = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 10,
                placeholdersEnabled = false
            )
        )

        // then
        assertThat(
            actual,
            isEqualTo(
                PagingSource.LoadResult.Page(
                    data = MockData.photoList,
                    prevKey = null,
                    nextKey = 2
                )
            )
        )
    }

    private class FakePhotoRemoteRepository : PhotoRemoteRepository {
        override suspend fun getPhoto(page: Int) = PhotoPagingModel(
            currentPage = 1,
            prevPage = null,
            nextPage = 2,
            photoList = MockData.photoList
        )
    }

}