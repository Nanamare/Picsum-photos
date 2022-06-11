package com.nanamare.starter.util

import android.content.Context
import androidx.annotation.DimenRes
import kotlin.math.max

fun getSpanCount(context: Context, imageWidthPx: Int): Int = getScreenWidth(context) / imageWidthPx

fun getOptimizedSpanCount(
    context: Context,
    @DimenRes imageWidthResId: Int,
    minSpan: Int = 1
): Int {
    val imageWidthPx = context.resources.getDimensionPixelSize(imageWidthResId)
    return max(getSpanCount(context, imageWidthPx), minSpan)
}