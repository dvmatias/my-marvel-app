package com.cmdv.domain.repository

import com.cmdv.domain.model.CharacterModel
import com.cmdv.domain.model.GetCharactersResponseModel
import com.cmdv.domain.utils.LiveDataStatusWrapper

interface CharactersRepository {
    fun getCharacters(limit: Int, offset: Int): LiveDataStatusWrapper<GetCharactersResponseModel>
    fun addFavourite(character: CharacterModel, position: Int): LiveDataStatusWrapper<Int>
    fun removeFavourite(character: CharacterModel, position: Int): LiveDataStatusWrapper<Int>
}