package com.nanamare.starter.initializer

import android.content.Context
import androidx.startup.Initializer
import com.nanamare.starter.BuildConfig
import timber.log.Timber

class TimberInitializer : Initializer<Unit> {

    override fun create(context: Context) {
        if (BuildConfig.DEBUG) {
            Timber.plant(object : Timber.DebugTree() {
                override fun createStackElementTag(element: StackTraceElement) =
                    "PickPick://${element.fileName}:${element.lineNumber}#${element.methodName}"
            })
        }
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> = mutableListOf()

}