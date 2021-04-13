package com.cmdv.data.mapper

import com.cmdv.data.entity.CharacterEntity
import com.cmdv.domain.base.BaseMapper
import com.cmdv.domain.model.CharacterModel

object CharacterMapper : BaseMapper<CharacterEntity, CharacterModel>() {

    override fun transformEntityToModel(e: CharacterEntity): CharacterModel {
        return super.transformEntityToModel(e)
    }
}