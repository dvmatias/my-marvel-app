package com.cmdv.data.repository

import com.cmdv.data.mapper.CharacterRoomMapper
import com.cmdv.data.mapper.GetCharactersResponseMapper
import com.cmdv.data.source.dao.FavouriteCharactersDao
import com.cmdv.data.source.service.CharactersApi
import com.cmdv.data.utils.NetworkHandler
import com.cmdv.data.utils.NetworkManager
import com.cmdv.domain.model.CharacterModel
import com.cmdv.domain.model.GetCharactersResponseModel
import com.cmdv.domain.repository.CharactersRepository
import com.cmdv.domain.utils.LiveDataStatusWrapper

class CharactersRepositoryImpl(
    private val charactersApi: CharactersApi,
    private val favouriteCharactersDao: FavouriteCharactersDao,
    networkHandler: NetworkHandler
) : CharactersRepository, NetworkManager(networkHandler) {

    override fun getCharacters(
        limit: Int,
        offset: Int
    ): LiveDataStatusWrapper<GetCharactersResponseModel> {
        return doNetworkRequest(charactersApi.getCharacters(limit, offset)) {
            GetCharactersResponseMapper.transformEntityToModel(it)
        }
    }

    override fun addFavourite(character: CharacterModel, position: Int): LiveDataStatusWrapper<Int> {
        val roomEntity = CharacterRoomMapper.transformModelToEntity(character)
        favouriteCharactersDao.insertFavourite(roomEntity)
        return LiveDataStatusWrapper.success(position)

    }

    override fun removeFavourite(id: Int): LiveDataStatusWrapper<Boolean> {
        TODO("Not yet implemented")
    }
}