package com.cmdv.feature.characters.listener

interface CharactersFragmentListener {
    fun onCharacterClick(characterId: Int)
    fun showErrorSnackBar(message: String)
}