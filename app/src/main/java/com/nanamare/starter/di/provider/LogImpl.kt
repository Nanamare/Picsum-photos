package com.nanamare.starter.di.provider

import com.nanamare.domain.base.provider.Log
import timber.log.Timber

class LogImpl : Log {

    override fun d(log: String) {
        Timber.d(log)
    }

    override fun e(e: Throwable) {
        Timber.e(e)
    }
}