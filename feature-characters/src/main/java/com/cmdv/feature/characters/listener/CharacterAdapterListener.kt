package com.cmdv.feature.characters.listener

interface CharacterAdapterListener {
    fun onLoadMoreCharacters(offset: Int)
    fun onCharacterClick(characterId: Int)
    fun onFavoriteClick(position: Int, isFavourite: Boolean)
}