package com.cmdv.domain.repository

import com.cmdv.domain.model.CharacterModel
import com.cmdv.domain.utils.LiveDataStatusWrapper

interface CharacterRepository {
    fun getTotalCharacters(): LiveDataStatusWrapper<Int>
    fun getCharacters(loadMore: Boolean, limit: Int, offset: Int): LiveDataStatusWrapper<List<CharacterModel>>
}