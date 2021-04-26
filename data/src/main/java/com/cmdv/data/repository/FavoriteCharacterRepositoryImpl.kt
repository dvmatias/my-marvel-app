package com.cmdv.data.repository

import com.cmdv.data.entity.FavoriteCharacterRoomEntity
import com.cmdv.data.mapper.CharacterRoomMapper
import com.cmdv.data.source.dao.CharactersDao
import com.cmdv.data.source.dao.FavoriteCharactersDao
import com.cmdv.domain.model.CharacterModel
import com.cmdv.domain.repository.FavoriteCharacterRepository
import com.cmdv.domain.utils.Event
import com.cmdv.domain.utils.LiveDataStatusWrapper

class FavoriteCharacterRepositoryImpl(
    private val charactersDao: CharactersDao,
    private val favoriteCharactersDao: FavoriteCharactersDao
) : FavoriteCharacterRepository {


    override fun getFavorites(): LiveDataStatusWrapper<List<CharacterModel>> {
        val favoriteCharacterIds = favoriteCharactersDao.getAll().map {
            it.characterId
        }
        val favoriteCharacters = charactersDao.getById(favoriteCharacterIds).map {
            CharacterRoomMapper.transformEntityToModel(it)
        }
        return LiveDataStatusWrapper.success(favoriteCharacters)
    }

    override fun addFavorite(
        characterId: Int,
        position: Int
    ): LiveDataStatusWrapper<Event<Int>> {
        val roomEntity = FavoriteCharacterRoomEntity(null, characterId)
        favoriteCharactersDao.insert(roomEntity)
        updateModel()
        return LiveDataStatusWrapper.success(Event(position))
    }

    override fun removeFavorite(
        character: CharacterModel,
        position: Int
    ): LiveDataStatusWrapper<Event<Int>> {
        favoriteCharactersDao.delete(character.id)
        updateModel()
        return LiveDataStatusWrapper.success(Event(position))
    }

    override fun removeAll(): LiveDataStatusWrapper<Event<Int>> {
        favoriteCharactersDao.deleteAll()
        updateModel()
        return LiveDataStatusWrapper.success(Event(1))
    }

    private fun updateModel() = kotlin.run {
        charactersDao.getAll().forEach {
            it.isFavorite = favoriteCharactersDao.getById(it.characterId) != null
        }
    }

}