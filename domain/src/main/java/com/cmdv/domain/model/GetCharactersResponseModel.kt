package com.cmdv.domain.model

data class GetCharactersResponseModel(
    val data: DataModel
) {
    data class DataModel(
        val offset: Int,
        val limit: Int,
        val total: Int,
        val count: Int,
        val results: List<CharacterModel>
    )
}
