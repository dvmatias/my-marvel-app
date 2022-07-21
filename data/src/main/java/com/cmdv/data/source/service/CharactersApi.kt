package com.cmdv.data.source.service

import com.cmdv.data.entity.GetCharactersResponseEntity
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CharactersApi {
    companion object {
        private const val ROOTH_PATH = "v1/public/"

        private const val QUERY_LIMIT = "limit"
        private const val QUERY_OFFSET = "offset"
        private const val PATH_CHARACTER_ID = "characterId"

        private const val EP_CHARACTERS = ROOTH_PATH.plus("characters")
        private const val EP_CHARACTER = ROOTH_PATH.plus("characters/{$PATH_CHARACTER_ID}")
    }

    @GET(EP_CHARACTERS)
    fun getCharacters(
        @Query(QUERY_LIMIT) limit: Int,
        @Query(QUERY_OFFSET) offset: Int
    ): Call<GetCharactersResponseEntity>

    @GET(EP_CHARACTER)
    fun getCharacterById(@Path(PATH_CHARACTER_ID) characterId: Int): Call<GetCharactersResponseEntity>
}