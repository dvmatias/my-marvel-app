package com.cmdv.common.extensions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cmdv.domain.utils.FailureType
import com.cmdv.domain.utils.LiveDataStatusWrapper

fun <T> MutableLiveData<LiveDataStatusWrapper<ArrayList<T>>>.plusAssign(newValue: LiveDataStatusWrapper<List<T>>) {
    val totalItems = arrayListOf<T>()
    val oldItems: ArrayList<T> = this.value?.data ?: arrayListOf()
    totalItems.addAll(oldItems)
    totalItems.addAll(newValue.data ?: arrayListOf())

    this.value = when (newValue.status) {
        LiveDataStatusWrapper.Status.LOADING -> LiveDataStatusWrapper.loading(totalItems)
        LiveDataStatusWrapper.Status.SUCCESS -> LiveDataStatusWrapper.success(totalItems)
        LiveDataStatusWrapper.Status.ERROR -> LiveDataStatusWrapper.error(
            totalItems,
            this.value?.failureType ?: FailureType.LocalError
        )
    }
}

fun <T> LiveData<T>.emit(shadow: MutableLiveData<T>) {
    shadow.value = this.value
}