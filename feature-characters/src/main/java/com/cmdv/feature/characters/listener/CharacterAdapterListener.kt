package com.cmdv.feature.characters.listener

import android.view.View

interface CharacterAdapterListener {
    fun loadMore(offset: Int)
    fun onCharacterClick(view: View, id: Int)
}