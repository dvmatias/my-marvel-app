package com.cmdv.data.repository

import com.cmdv.data.mapper.CharacterRoomMapper
import com.cmdv.data.mapper.GetCharactersResponseMapper
import com.cmdv.data.source.dao.FavouriteCharactersDao
import com.cmdv.data.source.service.CharactersApi
import com.cmdv.data.utils.NetworkHandler
import com.cmdv.data.utils.NetworkManager
import com.cmdv.domain.model.CharacterModel
import com.cmdv.domain.repository.CharactersRepository
import com.cmdv.domain.utils.Event
import com.cmdv.domain.utils.LiveDataStatusWrapper

class CharactersRepositoryImpl(
    private val charactersApi: CharactersApi,
    private val favouriteCharactersDao: FavouriteCharactersDao,
    networkHandler: NetworkHandler
) : CharactersRepository, NetworkManager(networkHandler) {

    override fun getTotalCharacters(): LiveDataStatusWrapper<Int> {
        return doNetworkRequest(charactersApi.getCharacters(1, 0)) { response ->
            response.data?.total ?: 0
        }
    }

    override fun getCharacters(
        limit: Int,
        offset: Int
    ): LiveDataStatusWrapper<ArrayList<CharacterModel>> {
        return doNetworkRequest(charactersApi.getCharacters(limit, offset)) {
            GetCharactersResponseMapper.transformEntityToModel(it).characters.onEach { character ->
                character.isFavourite = favouriteCharactersDao.getById(character.id) != null
            }
        }
    }

    override fun addFavourite(
        character: CharacterModel,
        position: Int
    ): LiveDataStatusWrapper<Event<Int>> {
        val roomEntity = CharacterRoomMapper.transformModelToEntity(character)
        favouriteCharactersDao.insertFavourite(roomEntity)
        return LiveDataStatusWrapper.success(Event(position))

    }

    override fun removeFavourite(
        character: CharacterModel,
        position: Int
    ): LiveDataStatusWrapper<Event<Int>> {
        favouriteCharactersDao.delete(character.id)
        return LiveDataStatusWrapper.success(Event(position))
    }
}