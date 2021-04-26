package com.cmdv.feature.characters.fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cmdv.domain.model.CharacterModel
import com.cmdv.domain.usecase.GetFavoriteCharactersUseCase
import com.cmdv.domain.usecase.RemoveAllFavoriteCharacterUseCase
import com.cmdv.domain.usecase.RemoveFavoriteCharacterUseCase
import com.cmdv.domain.utils.Event
import com.cmdv.domain.utils.LiveDataStatusWrapper
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class FavoritesViewModel(
    private val getFavoriteCharactersUseCase: GetFavoriteCharactersUseCase,
    private val removeFavoriteCharacterUseCase: RemoveFavoriteCharacterUseCase,
    private val removeAllFavoriteCharacterUseCase: RemoveAllFavoriteCharacterUseCase
) : ViewModel() {

    private val _favoriteCharacters =
        MutableLiveData<LiveDataStatusWrapper<List<CharacterModel>>>()
    val favoriteCharacters: LiveData<LiveDataStatusWrapper<List<CharacterModel>>>
        get() = _favoriteCharacters

    private val _removeAll = MutableLiveData<Event<Int>>()
    val removeAll: LiveData<Event<Int>>
        get() = _removeAll

    init {
        getFavoritesCharacters()
    }

    fun getFavoritesCharacters() {
        viewModelScope.launch {
            val params = GetFavoriteCharactersUseCase.Params()
            getFavoriteCharactersUseCase(params).collect { statusWrapper ->
                _favoriteCharacters.value = statusWrapper
            }
        }
    }

    fun removeAll() {
        viewModelScope.launch {
            val params = RemoveAllFavoriteCharacterUseCase.Params()
            removeAllFavoriteCharacterUseCase(params).collect {
                it.data?.let { event ->
                    _removeAll.value = event
                }
            }
        }
    }

    fun removeFavoriteCharacter(position: Int) {
        viewModelScope.launch {
            getCharacter(position).let { character ->
                val params = RemoveFavoriteCharacterUseCase.Params(character, position)
                removeFavoriteCharacterUseCase(params).collect {
                    if (it.status == LiveDataStatusWrapper.Status.SUCCESS) {
                        getFavoritesCharacters()
                    }
                }
            }
        }
    }

    private fun getCharacter(position: Int): CharacterModel =
        favoriteCharacters.value?.data?.get(position) ?: throw IllegalStateException("")

}