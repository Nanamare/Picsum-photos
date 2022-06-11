package com.nanamare.starter.util

import android.app.Activity
import kotlin.properties.ReadOnlyProperty

fun stringExtra(defaultValue: String) = ReadOnlyProperty<Activity, String?> { thisRef, property ->
    thisRef.intent.extras?.getString(property.name, defaultValue)
}