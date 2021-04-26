package com.cmdv.domain.repository

import com.cmdv.domain.model.CharacterModel
import com.cmdv.domain.utils.Event
import com.cmdv.domain.utils.LiveDataStatusWrapper

interface FavoriteCharacterRepository {
    fun addFavorite(character: CharacterModel, position: Int): LiveDataStatusWrapper<Event<Int>>
    fun removeFavorite(character: CharacterModel, position: Int): LiveDataStatusWrapper<Event<Int>>
    fun getFavorites(): LiveDataStatusWrapper<List<CharacterModel>>
    fun removeAll(): LiveDataStatusWrapper<Event<Int>>
}