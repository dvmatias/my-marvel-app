package com.cmdv.data.mapper

import com.cmdv.data.entity.CharacterEntity
import com.cmdv.data.entity.GetCharactersResponseEntity
import com.cmdv.domain.base.BaseMapper
import com.cmdv.domain.model.CharacterModel
import com.cmdv.domain.model.GetCharactersResponseModel

private const val DEFAULT_INT = 1

object GetCharactersResponseMapper :
    BaseMapper<GetCharactersResponseEntity, GetCharactersResponseModel>() {

    override fun transformEntityToModel(e: GetCharactersResponseEntity): GetCharactersResponseModel {
        return GetCharactersResponseModel(
            total = e.data?.total ?: DEFAULT_INT,
            characters = transformCharacters(e.data?.results)
        )
    }

    private fun transformCharacters(
        results: ArrayList<CharacterEntity>?
    ): ArrayList<CharacterModel> {
        val characters = arrayListOf<CharacterModel>()
        results?.map {
            characters.add(CharacterMapper.transformEntityToModel(it))
        }
        return characters
    }

}