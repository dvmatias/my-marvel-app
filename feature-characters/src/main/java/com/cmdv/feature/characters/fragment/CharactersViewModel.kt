package com.cmdv.feature.characters.fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cmdv.domain.model.CharacterModel
import com.cmdv.domain.usecase.AddFavoriteCharacterUseCase
import com.cmdv.domain.usecase.GetCharactersUseCase
import com.cmdv.domain.usecase.GetTotalCharactersUseCase
import com.cmdv.domain.usecase.RemoveFavoriteCharacterUseCase
import com.cmdv.domain.utils.Event
import com.cmdv.domain.utils.LiveDataStatusWrapper
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

private const val LIMIT_CHARACTERS_FETCH_DEFAULT = 32
private const val OFFSET_CHARACTERS_FETCH_DEFAULT = 0

@ExperimentalCoroutinesApi
class CharactersViewModel(
    private val getTotalCharactersUseCase: GetTotalCharactersUseCase,
    private val getCharactersUseCase: GetCharactersUseCase,
    private val addFavoriteCharacterUseCase: AddFavoriteCharacterUseCase,
    private val removeFavoriteCharacterUseCase: RemoveFavoriteCharacterUseCase
) : ViewModel() {
    private val isAllCharactersLoaded: Boolean
        get() = characters.origianl.value?.data?.size.let { totalCharactersCount == it }

    private var totalCharactersCount: Int = 0

    val characters = ObservableDataStatus<List<CharacterModel>>()

    private val _addedFavoritePosition = MutableLiveData<Event<Int>>()
    val addedFavoritePosition: LiveData<Event<Int>>
        get() = _addedFavoritePosition

    private val _removedFavoritePosition = MutableLiveData<Event<Int>>()
    val removedFavoritePosition: LiveData<Event<Int>>
        get() = _removedFavoritePosition

    fun init() {
        getCharacters(fetch = false)
    }

    private fun getTotalCharacters() {
        viewModelScope.launch {
            val params = GetTotalCharactersUseCase.Params()
            getTotalCharactersUseCase(params).collect { statusWrapper ->
                totalCharactersCount = statusWrapper.data ?: 0
                if (totalCharactersCount != 0) {
                    getCharacters(fetch = true)
                }
            }
        }
    }

    fun getCharacters(
        fetch: Boolean,
        limit: Int = LIMIT_CHARACTERS_FETCH_DEFAULT,
        offset: Int = OFFSET_CHARACTERS_FETCH_DEFAULT
    ) {
        if (!isAllCharactersLoaded) {
            val params = GetCharactersUseCase.Params(fetch, limit, offset)
            viewModelScope.launch {
                getCharactersUseCase(params).collect { statusWrapper ->
                    characters.set(statusWrapper)
                }
            }
        }
    }

    fun addFavorite(characterId: Int, position: Int) {
        val params = AddFavoriteCharacterUseCase.Params(characterId, position)
        viewModelScope.launch {
            addFavoriteCharacterUseCase(params).collect {
                it.data?.let { event ->
                    _addedFavoritePosition.value = event
                }
            }
        }
    }

    fun removeFavorite(characterId: Int, position: Int) {
        val params = RemoveFavoriteCharacterUseCase.Params(characterId, position)
        viewModelScope.launch {
            removeFavoriteCharacterUseCase(params).collect {
                it.data?.let { event ->
                    _removedFavoritePosition.value = event
                }
            }
        }
    }
}

class ObservableDataStatus<M> {
    private val _shadow = MutableLiveData<LiveDataStatusWrapper<M>>()
    val origianl: LiveData<LiveDataStatusWrapper<M>>
        get() = _shadow

    fun set(value: LiveDataStatusWrapper<M>) {
        _shadow.value = value
    }
}

class ObservableDataEvent<M> {
    private val _shadow = MutableLiveData<Event<M>>()
    val origianl: LiveData<Event<M>>
        get() = _shadow

    fun set(value: Event<M>) {
        _shadow.value = value
    }
}