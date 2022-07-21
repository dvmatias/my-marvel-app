package com.cmdv.feature.characterdetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cmdv.domain.model.CharacterModel
import com.cmdv.domain.usecase.GetCharacterByIdUserCase
import com.cmdv.domain.utils.LiveDataStatusWrapper
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class CharacterDetailsViewModel constructor(
    private val getCharacterByIdUserCase: GetCharacterByIdUserCase

) : ViewModel() {

    private val _character = MutableLiveData<LiveDataStatusWrapper<CharacterModel>>()
    val character: LiveData<LiveDataStatusWrapper<CharacterModel>>
        get() = _character

    fun getCharacter(characterId: Int) {
        viewModelScope.launch {
            val params = GetCharacterByIdUserCase.Params(characterId)
            getCharacterByIdUserCase.invoke(params).collect {
                _character.value = it
            }
        }
    }
}