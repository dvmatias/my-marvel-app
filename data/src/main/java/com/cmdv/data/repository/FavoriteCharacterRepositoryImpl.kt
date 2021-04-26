package com.cmdv.data.repository

import com.cmdv.data.mapper.CharacterRoomMapper
import com.cmdv.data.source.dao.FavouriteCharactersDao
import com.cmdv.domain.model.CharacterModel
import com.cmdv.domain.repository.FavoriteCharacterRepository
import com.cmdv.domain.utils.Event
import com.cmdv.domain.utils.LiveDataStatusWrapper

class FavoriteCharacterRepositoryImpl(
    private val favouriteCharactersDao: FavouriteCharactersDao
) : FavoriteCharacterRepository {


    override fun getFavorites(): LiveDataStatusWrapper<List<CharacterModel>> {
        val characters = favouriteCharactersDao.getAll().map {
            CharacterRoomMapper.transformEntityToModel(it)
        }
        return LiveDataStatusWrapper.success(characters)
    }

    override fun addFavorite(
        character: CharacterModel,
        position: Int
    ): LiveDataStatusWrapper<Event<Int>> {
        val roomEntity = CharacterRoomMapper.transformModelToEntity(character)
        favouriteCharactersDao.insert(roomEntity)
        return LiveDataStatusWrapper.success(Event(position))

    }

    override fun removeFavorite(
        character: CharacterModel,
        position: Int
    ): LiveDataStatusWrapper<Event<Int>> {
        favouriteCharactersDao.delete(character.id)
        return LiveDataStatusWrapper.success(Event(position))
    }

    override fun removeAll(): LiveDataStatusWrapper<Event<Int>> {
        favouriteCharactersDao.deleteAll()
        return LiveDataStatusWrapper.success(Event(1))
    }
}