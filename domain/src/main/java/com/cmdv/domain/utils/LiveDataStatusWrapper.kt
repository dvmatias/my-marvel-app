package com.cmdv.domain.utils

import java.io.Serializable

class LiveDataStatusWrapper<out T>(
    val status: Status,
    val data: T?,
    val failureType: FailureType?
) : Serializable {

    enum class Status {
        LOADING,
        ERROR,
        SUCCESS
    }

    companion object {
        fun <T> success(data: T?): LiveDataStatusWrapper<T> =
            LiveDataStatusWrapper(Status.SUCCESS, data, null)

        fun <T> error(data: T? = null, failureType: FailureType): LiveDataStatusWrapper<T> =
            LiveDataStatusWrapper(Status.ERROR, data, failureType)

        fun <T> loading(data: T? = null): LiveDataStatusWrapper<T> =
            LiveDataStatusWrapper(Status.LOADING, data, null)
    }
}