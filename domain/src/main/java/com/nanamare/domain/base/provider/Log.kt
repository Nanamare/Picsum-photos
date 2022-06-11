package com.nanamare.domain.base.provider

interface Log {
    fun d(log: String)
    fun e(e: Throwable)
}