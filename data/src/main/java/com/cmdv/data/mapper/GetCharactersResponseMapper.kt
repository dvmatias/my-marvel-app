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
            transformData(e.data)
        )
    }

    private fun transformData(
        data: GetCharactersResponseEntity.DataEntity?
    ): GetCharactersResponseModel.DataModel =
        GetCharactersResponseModel.DataModel(
            data?.offset ?: DEFAULT_INT,
            data?.limit ?: DEFAULT_INT,
            data?.total ?: DEFAULT_INT,
            data?.count ?: DEFAULT_INT,
            transformResults(data?.results)
        )

    private fun transformResults(
        results: List<CharacterEntity>?
    ): List<CharacterModel> =
        results?.map {
            CharacterMapper.transformEntityToModel(it)
        } ?: kotlin.run { listOf() }

}