package com.cmdv.data.source.service

import com.cmdv.data.entity.GetCharactersResponseEntity
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CharactersApi {
    companion object {
        private const val ROOTH_PATH = "v1/public/"

        private const val EP_CHARACTERS = ROOTH_PATH.plus("characters")

        private const val QUERY_LIMIT = "limit"
        private const val QUERY_OFFSET = "offset"
    }

    @GET(EP_CHARACTERS)
    fun getCharacters(
        @Query(QUERY_LIMIT) limit: Int,
        @Query(QUERY_OFFSET) offset: Int
    ): Call<GetCharactersResponseEntity>
}