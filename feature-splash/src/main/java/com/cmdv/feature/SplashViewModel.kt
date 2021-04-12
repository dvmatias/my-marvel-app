package com.cmdv.feature

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cmdv.core.event.Event

class SplashViewModel : ViewModel() {

    private val newDestination: MutableLiveData<Event<Int>> = MutableLiveData()

    fun getDestination(): LiveData<Event<Int>> {
        return newDestination
    }

    fun setDestination(destinationId: Int) {
        newDestination.value = Event(destinationId)
    }

}