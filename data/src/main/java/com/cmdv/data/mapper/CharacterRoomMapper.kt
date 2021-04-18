package com.cmdv.data.mapper

import com.cmdv.data.entity.FavouriteCharacterRoomEntity
import com.cmdv.domain.base.BaseMapper
import com.cmdv.domain.model.CharacterModel

object CharacterRoomMapper : BaseMapper<FavouriteCharacterRoomEntity, CharacterModel>() {

    override fun transformEntityToModel(e: FavouriteCharacterRoomEntity): CharacterModel =
        CharacterModel(
            e.characterId,
            e.name,
            e.description,
            e.thumbnail,
            true
        )

    override fun transformModelToEntity(m: CharacterModel): FavouriteCharacterRoomEntity =
        FavouriteCharacterRoomEntity(
            null,
            m.id,
            m.name,
            m.description,
            m.thumbnail
        )
}