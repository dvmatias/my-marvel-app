package com.cmdv.domain.model

data class GetCharactersResponseModel(
    val total: Int,
    val characters: ArrayList<CharacterModel>
)
