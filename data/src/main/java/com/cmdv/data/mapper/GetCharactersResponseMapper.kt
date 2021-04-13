package com.cmdv.data.mapper

import com.cmdv.data.entity.GetCharactersResponseEntity
import com.cmdv.domain.base.BaseMapper
import com.cmdv.domain.model.GetCharactersResponseModel

object GetCharactersResponseMapper :
    BaseMapper<GetCharactersResponseEntity, GetCharactersResponseModel>() {

    override fun transformEntityToModel(e: GetCharactersResponseEntity): GetCharactersResponseModel {
        return super.transformEntityToModel(e)
    }
}