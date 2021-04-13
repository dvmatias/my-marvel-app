package com.cmdv.domain.base

import com.cmdv.domain.utils.LiveDataStatusWrapper
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

@ExperimentalCoroutinesApi
abstract class BaseUseCase<out R, in P> {

    @Suppress("UNCHECKED_CAST")
    open fun launch(params: P): Flow<R> =
        callbackFlow {
            offer(LiveDataStatusWrapper.loading(null) as R)
            offer(
                withContext(Dispatchers.Default) {
                    run(params)
                }
            )
            awaitClose { cancel() }
        }

    abstract suspend fun run(params: P): R

}