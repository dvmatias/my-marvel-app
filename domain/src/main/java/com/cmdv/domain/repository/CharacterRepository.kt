package com.cmdv.domain.repository

import com.cmdv.domain.model.CharacterModel
import com.cmdv.domain.utils.Event
import com.cmdv.domain.utils.LiveDataStatusWrapper

interface CharacterRepository {
    fun getTotalCharacters(): LiveDataStatusWrapper<Int>
    fun getCharacters(limit: Int, offset: Int): LiveDataStatusWrapper<ArrayList<CharacterModel>>
}