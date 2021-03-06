package com.nanamare.domain.base.usecase

import com.nanamare.domain.base.provider.Log
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * https://github.com/google/iosched 참고
 * Executes business logic synchronously or asynchronously using Coroutines.
 */
abstract class NonParamCoroutineUseCase<R>(private val coroutineDispatcher: CoroutineDispatcher) {

    @Inject
    lateinit var log: Log

    /** Executes the use case asynchronously and returns a [Result].
     *
     * @return a [Result].
     */
    suspend operator fun invoke(): Result<R> {
        return try {
            // Moving all use case's executions to the injected dispatcher
            // In production code, this is usually the Default dispatcher (background thread)
            // In tests, this becomes a TestCoroutineDispatcher
            withContext(coroutineDispatcher) {
                execute().let {
                    Result.success(it)
                }
            }
        } catch (e: Exception) {
            log.e(e)
            Result.failure(e)
        }
    }

    /**
     * Override this to set the code to be executed.
     */
    @Throws(RuntimeException::class)
    protected abstract suspend fun execute(): R
}
