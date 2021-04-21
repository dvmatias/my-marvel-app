package com.cmdv.domain.repository

import com.cmdv.domain.model.CharacterModel
import com.cmdv.domain.utils.Event
import com.cmdv.domain.utils.LiveDataStatusWrapper

interface CharactersRepository {
    fun getTotalCharacters(): LiveDataStatusWrapper<Int>
    fun getCharacters(limit: Int, offset: Int): LiveDataStatusWrapper<ArrayList<CharacterModel>>
    fun addFavourite(character: CharacterModel, position: Int): LiveDataStatusWrapper<Event<Int>>
    fun removeFavourite(character: CharacterModel, position: Int): LiveDataStatusWrapper<Event<Int>>
}