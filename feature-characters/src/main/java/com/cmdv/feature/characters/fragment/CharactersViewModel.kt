package com.cmdv.feature.characters.fragment

import androidx.lifecycle.*
import com.cmdv.common.extensions.emit
import com.cmdv.common.extensions.plusAssign
import com.cmdv.domain.model.CharacterModel
import com.cmdv.domain.usecase.AddFavoriteCharacterUseCase
import com.cmdv.domain.usecase.GetCharactersUseCase
import com.cmdv.domain.usecase.GetTotalCharactersUseCase
import com.cmdv.domain.usecase.RemoveFavoriteCharacterUseCase
import com.cmdv.domain.utils.Event
import com.cmdv.domain.utils.LiveDataStatusWrapper
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect

private const val LIMIT_CHARACTERS_FETCH_DEFAULT = 21
private const val OFFSET_CHARACTERS_FETCH_DEFAULT = 0

@InternalCoroutinesApi
@ExperimentalCoroutinesApi
class CharactersViewModel(
    private val getTotalCharactersUseCase: GetTotalCharactersUseCase,
    private val getCharactersUseCase: GetCharactersUseCase,
    private val addFavoriteCharacterUseCase: AddFavoriteCharacterUseCase,
    private val removeFavoriteCharacterUseCase: RemoveFavoriteCharacterUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val isAllCharactersLoaded: Boolean
        get() = characters.value?.data?.size.let { totalCharactersCount == it }

    private var totalCharactersCount: Int = savedStateHandle.get<Int>("TOTAL_CHARACTERS") ?: 0

    private val _characters: MutableLiveData<LiveDataStatusWrapper<ArrayList<CharacterModel>>> =
        savedStateHandle.getLiveData("CHARACTERS")
    val characters: LiveData<LiveDataStatusWrapper<ArrayList<CharacterModel>>>
        get() = _characters

    private val _addedFavoritePosition = MutableLiveData<Event<Int>>()
    val addedFavoritePosition: LiveData<Event<Int>>
        get() = _addedFavoritePosition

    private val _removedFavoritePosition = MutableLiveData<Event<Int>>()
    val removedFavoritePosition: LiveData<Event<Int>>
        get() = _removedFavoritePosition

    fun init() {
        if (savedStateHandle.get<Int>("TOTAL_CHARACTERS") == null) {
            getTotalCharacters()
        } else {
            _characters.value = LiveDataStatusWrapper.success(characters.value?.data)
        }
    }

    private fun getTotalCharacters() {
        viewModelScope.launch {
            val params = GetTotalCharactersUseCase.Params()
            getTotalCharactersUseCase(params).collect { statusWrapper ->
                totalCharactersCount = statusWrapper.data ?: 0
                if (totalCharactersCount != 0) {
                    getCharacters()
                }
            }
        }
    }

    fun getCharacters(
        refresh: Boolean = false,
        limit: Int = LIMIT_CHARACTERS_FETCH_DEFAULT,
        offset: Int = OFFSET_CHARACTERS_FETCH_DEFAULT
    ) {
        if (!isAllCharactersLoaded) {
            val params = GetCharactersUseCase.Params(limit, offset)
            CoroutineScope(Dispatchers.Main.immediate).launch {
                getCharactersUseCase(params).collect { statusWrapper ->
                    if (refresh) {
                        _characters.value = statusWrapper
                    } else {
                        _characters.plusAssign(statusWrapper)
                    }
                }
            }
        }
    }

    fun addFavorite(position: Int) {
        getCharacter(position).let { character ->
            val params = AddFavoriteCharacterUseCase.Params(character, position)
            CoroutineScope(Dispatchers.Main.immediate).launch {
                addFavoriteCharacterUseCase(params).collect {
                    it.data?.let { event ->
                        _addedFavoritePosition.value = event
                    }
                }
            }
        }
    }

    fun removeFavorite(position: Int) {
        getCharacter(position).let { character ->
            val params = RemoveFavoriteCharacterUseCase.Params(character, position)
            CoroutineScope(Dispatchers.Main.immediate).launch {
                removeFavoriteCharacterUseCase(params).collect {
                    it.data?.let { event ->
                        _removedFavoritePosition.value = event
                    }
                }
            }
        }
    }

    fun updateCharacterFavoriteStatus(position: Int, isFavourite: Boolean) {
        val character = getCharacter(position)
        character.isFavourite = isFavourite
        characters.emit(_characters)
    }

    private fun getCharacter(position: Int): CharacterModel =
        characters.value?.data?.get(position) ?: throw IllegalStateException("")

}