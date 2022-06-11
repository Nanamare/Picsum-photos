package com.nanamare.starter.util

import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.imageLoader
import coil.request.Disposable
import coil.request.ErrorResult
import coil.request.ImageRequest
import coil.request.SuccessResult
import coil.size.Size
import coil.size.SizeResolver
import coil.size.ViewSizeResolver
import coil.transform.CircleCropTransformation
import coil.transform.RoundedCornersTransformation
import coil.transform.Transformation
import timber.log.Timber

@BindingAdapter(
    "img",
    "withoutResizing",
    "placeHolder",
    "errorPlaceHolder",
    "isCrossFade",
    "roundCorner",
    "isCircleCrop",
    requireAll = false
)
fun ImageView.load(
    img: Any?,
    withoutResizing: Boolean = false,
    placeHolder: Drawable? = null,
    errorPlaceHolder: Drawable? = null,
    isCrossFade: Boolean = false,
    roundCorner: Int = 0,
    isCircleCrop: Boolean = false,
): Disposable {
    val request = imageRequestBuilder(
        placeHolder = placeHolder,
        errorPlaceHolder = errorPlaceHolder,
        img = img,
        withoutResizing = withoutResizing
    ).applyTransformation(isCircleCrop, roundCorner, isCrossFade)
        .build()

    return context.imageLoader.enqueue(request)
}

private fun ImageRequest.Builder.applyTransformation(
    isCircleCrop: Boolean,
    roundCorner: Int,
    isCrossFade: Boolean
): ImageRequest.Builder {
    val transformationList = mutableListOf<Transformation>()

    if (isCircleCrop) {
        transformationList.add(CircleCropTransformation())
    }

    if (roundCorner != 0) {
        transformationList.add(RoundedCornersTransformation(roundCorner.toPx.toFloat()))
    }

    if (isCrossFade) {
        crossfade(true)
    }

    transformations(transformationList)

    return this
}

private fun ImageView.imageRequestBuilder(
    placeHolder: Drawable?,
    errorPlaceHolder: Drawable?,
    img: Any?,
    withoutResizing: Boolean,
): ImageRequest.Builder {
    return ImageRequest.Builder(context)
        .placeholder(placeHolder)
        .error(errorPlaceHolder)
        .fallback(errorPlaceHolder)
        .data(img)
        .size(
            if (withoutResizing) {
                SizeResolver(Size.ORIGINAL)
            } else {
                ViewSizeResolver(this)
            }
        )
        .listener(
            onStart = { Timber.tag(TAG).d("Start load image") },
            onCancel = { Timber.tag(TAG).d("Cancel load image") },
            onSuccess = { request: ImageRequest, successResult: SuccessResult ->
                Timber.tag(TAG)
                    .d("Success request ${successResult.request.data} from ${successResult.dataSource} Hit")
            },
            onError = { request: ImageRequest, errorResult: ErrorResult ->
                Timber.tag(TAG).e("Error request : $request} ${errorResult.throwable}")
            }
        )
        // ImageView Target 으로 하지 않으면 취소하지 않고, OneShotDisposable 되어 다른 이미지가 보일 수 있음
        // https://github.com/coil-kt/coil/blob/main/coil-base/src/main/java/coil/RealImageLoader.kt#L118 참고
        .target(this)
}

val Int.toPx: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()

private const val TAG = "Coil"