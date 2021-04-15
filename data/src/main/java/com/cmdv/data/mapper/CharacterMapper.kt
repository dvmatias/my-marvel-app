package com.cmdv.data.mapper

import com.cmdv.data.entity.CharacterEntity
import com.cmdv.domain.base.BaseMapper
import com.cmdv.domain.model.CharacterModel

private const val DEFAULT_INT = 1
private const val DEFAULT_STRING = ""

object CharacterMapper : BaseMapper<CharacterEntity, CharacterModel>() {

    override fun transformEntityToModel(e: CharacterEntity): CharacterModel =
        CharacterModel(
            e.id ?: DEFAULT_INT,
            e.name ?: DEFAULT_STRING,
            e.description ?: DEFAULT_STRING,
            transformThumbnail(e.thumbnail)
        )

    private fun transformThumbnail(thumbnail: CharacterEntity.ThumbnailEntity?): String {
        val path = thumbnail?.path ?: DEFAULT_STRING
        val extension = thumbnail?.extension ?: DEFAULT_STRING
        return if (path.isNotEmpty() && extension.isNotEmpty()) {
            "$path.$extension"
        } else {
            DEFAULT_STRING
        }
    }
}