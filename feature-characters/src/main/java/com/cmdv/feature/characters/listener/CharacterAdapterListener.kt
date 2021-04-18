package com.cmdv.feature.characters.listener

import android.view.View

interface CharacterAdapterListener {
    fun loadMore(offset: Int)
    fun onCharacterClick(view: View, characterId: Int)
    fun toggleFavouriteStatus(position: Int, isFavourite: Boolean)
}