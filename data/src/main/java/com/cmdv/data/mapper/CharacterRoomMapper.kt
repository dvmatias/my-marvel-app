package com.cmdv.data.mapper

import com.cmdv.data.entity.CharacterRoomEntity
import com.cmdv.domain.base.BaseMapper
import com.cmdv.domain.model.CharacterModel

object CharacterRoomMapper : BaseMapper<CharacterRoomEntity, CharacterModel>() {

    override fun transformEntityToModel(e: CharacterRoomEntity): CharacterModel =
        CharacterModel(
            e.characterId,
            e.name,
            e.description,
            e.thumbnail,
            e.isFavorite
        )

    override fun transformModelToEntity(m: CharacterModel): CharacterRoomEntity =
        CharacterRoomEntity(
            null,
            m.id,
            m.name,
            m.description,
            m.thumbnail,
            m.isFavourite
        )
}