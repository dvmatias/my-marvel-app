package com.cmdv.feature.characters

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.cmdv.domain.model.CharacterModel
import com.cmdv.domain.model.GetCharactersResponseModel
import com.cmdv.domain.usecase.AddFavouriteCharacterUseCase
import com.cmdv.domain.usecase.GetCharactersUseCase
import com.cmdv.domain.usecase.RemoveFavouriteCharacterUseCase
import com.cmdv.domain.utils.LiveDataStatusWrapper
import kotlinx.coroutines.ExperimentalCoroutinesApi

private const val LIMIT_CHARACTERS_FETCH_DEFAULT = 21
private const val OFFSET_CHARACTERS_FETCH_DEFAULT = 0

@ExperimentalCoroutinesApi
class CharactersViewModel(
    private val getCharactersUseCase: GetCharactersUseCase,
    private val addFavouriteCharacterUseCase: AddFavouriteCharacterUseCase,
    private val removeFavouriteCharacterUseCase: RemoveFavouriteCharacterUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private var isAllCharactersLoaded: Boolean = false

    private var totalCharactersCount: Int = 0
        set(value) {
            field = if (field == 0) value else 0
        }

    private val characters: MutableLiveData<List<CharacterModel>> =
        savedStateHandle.getLiveData("CHARACTERS")

    private val _lastFetchedCharacters =
        MutableLiveData<LiveDataStatusWrapper<GetCharactersResponseModel>>()
    val lastFetchedCharacters = _lastFetchedCharacters

    private val _newFavouritePosition =
        MutableLiveData<LiveDataStatusWrapper<Int>>()
    val newFavouritePosition = _newFavouritePosition

    private val _characterRemovedFromFavourites: MutableLiveData<LiveDataStatusWrapper<Int>> = MutableLiveData()
    val characterRemovedFromFavourites: LiveData<LiveDataStatusWrapper<Int>>
        get() = _characterRemovedFromFavourites

    fun getFirstTimeCharacters(
        limit: Int = LIMIT_CHARACTERS_FETCH_DEFAULT,
        offset: Int = OFFSET_CHARACTERS_FETCH_DEFAULT
    ) {
        if (savedStateHandle.get<MutableLiveData<List<CharacterModel>>>("CHARACTERS") != null) {
            _lastFetchedCharacters.value = LiveDataStatusWrapper.success(
                GetCharactersResponseModel(totalCharactersCount, characters.value ?: listOf())
            )
        } else {
            getMoreCharacters(limit, offset)
        }
    }

    fun getMoreCharacters(limit: Int = LIMIT_CHARACTERS_FETCH_DEFAULT, offset: Int) {
        if (!isAllCharactersLoaded) {
            getCharactersUseCase(
                params = GetCharactersUseCase.Params(limit, offset),
                wrapper = _lastFetchedCharacters
            )
        }
    }

    fun updateCharacters(loadCharacters: List<CharacterModel>) {
        val totalsCharacters: ArrayList<CharacterModel> = arrayListOf()
        characters.value?.let { saved -> totalsCharacters.addAll(saved) }
        totalsCharacters.addAll(loadCharacters)
        characters.value = totalsCharacters

        isAllCharactersLoaded = totalsCharacters.size == totalCharactersCount
    }

    fun addToFavourites(position: Int) {
        characters.value?.get(position)?.let { character ->
            addFavouriteCharacterUseCase(
                params = AddFavouriteCharacterUseCase.Params(character, position),
                wrapper = _newFavouritePosition
            )
        }
    }

    fun removeFromFavourites(position: Int) {
        characters.value?.get(position)?.let { character ->
            removeFavouriteCharacterUseCase(
                params = RemoveFavouriteCharacterUseCase.Params(character, position),
                wrapper = _characterRemovedFromFavourites
            )
        }
    }
}