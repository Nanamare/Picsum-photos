package com.nanamare.domain.photo.usecase

import com.nanamare.domain.base.IoDispatcher
import com.nanamare.domain.base.usecase.UseCase
import com.nanamare.domain.photo.model.PhotoPagingModel
import com.nanamare.domain.photo.repository.PhotoRemoteRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GetPhotoUseCase @Inject constructor(
    private val repository: PhotoRemoteRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : UseCase<Int, PhotoPagingModel>(dispatcher) {

    override suspend fun execute(param: Int): PhotoPagingModel = repository.getPhoto(param)

}