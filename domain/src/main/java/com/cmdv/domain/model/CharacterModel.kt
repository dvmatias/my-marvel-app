package com.cmdv.domain.model

import java.io.Serializable

data class CharacterModel(
    val id: Int,
    val name: String,
    val description: String,
    val thumbnail: String,
    var isFavourite: Boolean = false
) : Serializable