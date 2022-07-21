package com.cmdv.data.source.service

import com.cmdv.data.entity.GetCharactersResponseEntity
import retrofit2.Call
import retrofit2.Retrofit

class CharactersService(private val retrofit: Retrofit) : CharactersApi {
    private val charactersService by lazy {
        retrofit.create(CharactersApi::class.java)
    }

    override fun getCharacters(limit: Int, offset: Int): Call<GetCharactersResponseEntity> =
        charactersService.getCharacters(limit, offset)

    override fun getCharacterById(characterId: Int): Call<GetCharactersResponseEntity> =
        charactersService.getCharacterById(characterId)
}