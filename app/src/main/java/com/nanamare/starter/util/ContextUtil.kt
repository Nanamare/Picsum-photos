package com.nanamare.starter.util

import android.content.Context
import android.util.TypedValue
import androidx.annotation.AttrRes

fun Context.getResourceId(
    @AttrRes attribute: Int,
    typedValue: TypedValue = TypedValue(),
    resolveRefs: Boolean = true
): Int {
    theme.resolveAttribute(attribute, typedValue, resolveRefs)
    return typedValue.resourceId
}