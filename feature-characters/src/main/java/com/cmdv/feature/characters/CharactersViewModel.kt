package com.cmdv.feature.characters

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cmdv.domain.model.GetCharactersResponseModel
import com.cmdv.domain.usecase.GetCharactersUseCase
import com.cmdv.domain.utils.LiveDataStatusWrapper
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

private const val LIMIT_CHARACTERS_FETCH_DEFAULT = 25
private const val OFFSET_CHARACTERS_FETCH_DEFAULT = 0

@ExperimentalCoroutinesApi
class CharactersViewModel(
    private val getCharactersUseCase: GetCharactersUseCase
) : ViewModel() {

    private val _characters = MutableLiveData<LiveDataStatusWrapper<GetCharactersResponseModel>>()
    val characters: LiveData<LiveDataStatusWrapper<GetCharactersResponseModel>>
        get() = _characters

    init {
        getCharacters(LIMIT_CHARACTERS_FETCH_DEFAULT, OFFSET_CHARACTERS_FETCH_DEFAULT)
    }

    fun getCharacters(limit: Int, offset: Int) {
        viewModelScope.launch {
            getCharactersUseCase.launch(
                GetCharactersUseCase.Params(limit, offset)
            ).collect { response ->
                _characters.value = response
            }
        }
    }
}