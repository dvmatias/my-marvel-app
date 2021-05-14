package com.cmdv.data.repository

import android.util.Log
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
        Log.d("Shit! Repository", "addFavorite(characterId: $characterId, position: $position)")
        favoriteCharactersDao.insert(roomEntity)
        updateModel()
        return LiveDataStatusWrapper.success(Event(position))
    }

    override fun removeFavorite(
        characterId: Int,
        position: Int
    ): LiveDataStatusWrapper<Event<Int>> {
        favoriteCharactersDao.delete(characterId)
        updateModel()
        return LiveDataStatusWrapper.success(Event(position))
    }

    override fun removeAll(): LiveDataStatusWrapper<Event<Int>> {
        favoriteCharactersDao.deleteAll()
        updateModel()
        return LiveDataStatusWrapper.success(Event(1))
    }

    private fun updateModel() = kotlin.run {
        Log.d("Shit! Repository", "updateModel()")
        charactersDao.getAll().forEach {
            it.isFavorite = favoriteCharactersDao.getById(it.characterId) != null
            if (it.isFavorite) {
                Log.d("Shit! Repository", "${it.characterId} is Favorite")
            }
        }
    }

}