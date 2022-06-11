package com.nanamare.starter.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.nanamare.domain.photo.model.Photo
import com.nanamare.starter.BuildConfig
import com.nanamare.starter.ui.paging.PhotoPagingManager
import com.nanamare.starter.util.NetworkConnection
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import timber.log.Timber
import javax.inject.Inject

private const val StopTimeoutMillis: Long = 3000

interface PhotoViewModel {
    val photo: StateFlow<PagingData<Photo>>
    val error: SharedFlow<Throwable>
}

@HiltViewModel
class PhotoViewModelImpl @Inject constructor(
    photoPagingManager: PhotoPagingManager,
    networkConnection: NetworkConnection
) : ViewModel(), PhotoViewModel, NetworkConnection by networkConnection {

    private val _errorMsg = MutableSharedFlow<Throwable>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    override val error = _errorMsg.filter { BuildConfig.DEBUG }
        .shareIn(viewModelScope, SharingStarted.WhileSubscribed())

    override val photo: StateFlow<PagingData<Photo>> = photoPagingManager()
        .cachedIn(viewModelScope)
        .catch {
            _errorMsg.tryEmit(it)
            Timber.e(it)
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(StopTimeoutMillis), // Configuration Change 시 업스트림 이어 가도록 처리 약 3초 안에 해결된다고 가정
            PagingData.empty()
        )

}