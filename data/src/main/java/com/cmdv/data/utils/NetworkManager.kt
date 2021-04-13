package com.cmdv.data.utils

import com.cmdv.domain.utils.FailureType
import com.cmdv.domain.utils.LiveDataStatusWrapper
import retrofit2.Call

open class NetworkManager(private val networkHandler: NetworkHandler) {

    open fun <E, M> doNetworkRequest(call: Call<E>, transformResponse: (E) -> M): LiveDataStatusWrapper<M> {
        return try {
            when (networkHandler.isConnected) {
                true -> {
                    val response = call.execute()
                    with(response) {
                        val body = response.body()
                        if (isSuccessful && body != null) {
                            LiveDataStatusWrapper.success(transformResponse(body))
                        } else {
                            LiveDataStatusWrapper.error(failureType = FailureType.ServerError)
                        }
                    }
                }
                false, null -> LiveDataStatusWrapper.error(failureType = FailureType.ConnectionError)
            }
        } catch (e: Exception) {
            LiveDataStatusWrapper.error(null, FailureType.ServerError)
        }
    }

}