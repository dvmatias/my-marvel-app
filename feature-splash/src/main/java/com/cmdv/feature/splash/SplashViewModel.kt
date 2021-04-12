package com.cmdv.feature.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cmdv.core.event.Event

class SplashViewModel : ViewModel() {
    private val _newDestination: MutableLiveData<Event<Int>> = MutableLiveData()
    val newDestination: LiveData<Event<Int>>
        get() = _newDestination

    private val _goToCharactersActivity: MutableLiveData<Event<Boolean>> = MutableLiveData()
    val goToCharactersActivity: LiveData<Event<Boolean>>
        get() = _goToCharactersActivity

    fun setDestination(destinationId: Int) {
        _newDestination.value = Event(destinationId)
    }

    fun goToCharacters() {
        _goToCharactersActivity.value = Event(true)
    }
}