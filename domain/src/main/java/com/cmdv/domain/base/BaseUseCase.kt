package com.cmdv.domain.base

import com.cmdv.domain.utils.LiveDataStatusWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.withContext
import java.lang.Exception

@ExperimentalCoroutinesApi
abstract class BaseUseCase<R, in P> {

    abstract suspend fun run(params: P): R

    operator fun invoke(params: P): Flow<R> =
        callbackFlow {
            try {
                offer(withContext(Dispatchers.Main.immediate) { LiveDataStatusWrapper.loading(null) as R })
            } catch (e: Exception) { }
            offer(withContext(Dispatchers.Default) { run(params) })
            awaitClose { cancel() }
        }

}