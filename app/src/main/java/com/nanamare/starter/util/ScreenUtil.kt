package com.nanamare.starter.util

import android.content.Context
import android.graphics.Insets
import android.os.Build
import android.util.DisplayMetrics
import android.view.WindowInsets
import android.view.WindowManager
import androidx.core.content.getSystemService

/**
 * Throw exception
 */
fun getScreenWidth(context: Context): Int {
    val windowManager = context.getSystemService<WindowManager>() ?: error("WindowManager is null")
    return when {
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.R -> {
            val windowMetrics = windowManager.currentWindowMetrics
            val insets: Insets =
                windowMetrics.windowInsets.getInsetsIgnoringVisibility(WindowInsets.Type.systemBars())
            windowMetrics.bounds.width() - insets.left - insets.right
        }
        else -> {
            val displayMetrics = DisplayMetrics()
            windowManager.defaultDisplay.getMetrics(displayMetrics)
            displayMetrics.widthPixels
        }
    }
}