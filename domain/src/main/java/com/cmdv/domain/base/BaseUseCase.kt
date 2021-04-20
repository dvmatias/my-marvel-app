package com.cmdv.domain.base

import androidx.lifecycle.MutableLiveData
import com.cmdv.domain.utils.Event
import com.cmdv.domain.utils.LiveDataStatusWrapper
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collect

@ExperimentalCoroutinesApi
abstract class BaseUseCase<R, in P> {

    @Suppress("UNCHECKED_CAST")
    open fun launch(params: P): Flow<R> =
        callbackFlow {
            offer(withContext(Dispatchers.Main.immediate) { LiveDataStatusWrapper.loading(null) as R })
            offer(withContext(Dispatchers.Default) { run(params) })
            awaitClose { cancel() }
        }

    abstract suspend fun run(params: P): R

    operator fun invoke(params: P, wrapper: MutableLiveData<R>) {
        CoroutineScope(Dispatchers.Main.immediate).launch {
            val flow: Flow<R> = callbackFlow {
                offer(withContext(Dispatchers.Main.immediate) { LiveDataStatusWrapper.loading(null) as R })
                offer(withContext(Dispatchers.Default) { run(params) })
                awaitClose { cancel() }
            }

            flow.collect { wrapper.value = it }
        }
    }

}