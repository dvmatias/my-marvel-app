package com.cmdv.domain.model

data class IndexedFavoriteCharactersModel(
    val index: String,
    val favoriteCharacters: List<CharacterModel>
)
