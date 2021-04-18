package com.cmdv.feature.characters

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.cmdv.domain.model.CharacterModel
import com.cmdv.domain.model.GetCharactersResponseModel
import com.cmdv.domain.usecase.AddFavouriteCharacterUseCase
import com.cmdv.domain.usecase.GetCharactersUseCase
import com.cmdv.domain.usecase.GetCharactersUseCase.*
import com.cmdv.domain.usecase.RemoveFavouriteCharacterUseCase
import com.cmdv.domain.utils.LiveDataStatusWrapper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

private const val LIMIT_CHARACTERS_FETCH_DEFAULT = 21
private const val OFFSET_CHARACTERS_FETCH_DEFAULT = 0

@ExperimentalCoroutinesApi
class CharactersViewModel(
    private val getCharactersUseCase: GetCharactersUseCase,
    private val addFavouriteCharacterUseCase: AddFavouriteCharacterUseCase,
    private val removeFavouriteCharacterUseCase: RemoveFavouriteCharacterUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private var totalCharactersCount: Int = 0
        set(value) {
            field = if (field == 0) value else 0
        }

    val characters: MutableLiveData<List<CharacterModel>> =
        savedStateHandle.getLiveData("CHARACTERS")

    private val _lastFetchedCharacters =
        MutableLiveData<LiveDataStatusWrapper<GetCharactersResponseModel>>()
    val lastFetchedCharacters = _lastFetchedCharacters

    private val _newFavouritePosition =
        MutableLiveData<LiveDataStatusWrapper<Int>>()
    val newFavouritePosition = _newFavouritePosition

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
        if (!isAllCharactersLoaded()) {
            CoroutineScope(Dispatchers.Main.immediate).launch {
                getCharactersUseCase.launch(
                    Params(limit, offset)
                ).collect { response ->
                        _lastFetchedCharacters.value = response

                        updateCharacters(response.data?.characters)
                        totalCharactersCount = response.data?.total ?: 0
                    }
            }
        }
    }

    private fun isAllCharactersLoaded(): Boolean =
        characters.value?.size.let { charactersLoadedCount ->
            charactersLoadedCount == totalCharactersCount
        }

    private fun updateCharacters(loadCharacters: List<CharacterModel>?) {
        val totalsCharacters: ArrayList<CharacterModel> = arrayListOf()
        characters.value?.let { saved -> totalsCharacters.addAll(saved) }
        loadCharacters?.let { load -> totalsCharacters.addAll(load) }
        characters.value = totalsCharacters
    }

    fun addToFavourites(position: Int) {
        val character = characters.value?.get(position)
        character?.let {
            CoroutineScope(Dispatchers.Main.immediate).launch {
                addFavouriteCharacterUseCase.launch(
                    AddFavouriteCharacterUseCase.Params(character, position)
                ).collect {
                    newFavouritePosition.value = it
                }
            }
        }
    }

    fun removeFromFavourites(characterId: Int) {
        TODO("Not yet implemented")
    }
}