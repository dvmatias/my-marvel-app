package com.cmdv.data.repository

import com.cmdv.data.mapper.GetCharactersResponseMapper
import com.cmdv.data.source.service.CharactersApi
import com.cmdv.data.utils.NetworkHandler
import com.cmdv.data.utils.NetworkManager
import com.cmdv.domain.model.GetCharactersResponseModel
import com.cmdv.domain.repository.CharactersRepository
import com.cmdv.domain.utils.LiveDataStatusWrapper

class CharactersRepositoryImpl(
    private val charactersApi: CharactersApi,
//    private val charactersDao: CharactersDao, TODO
    networkHandler: NetworkHandler
) : CharactersRepository, NetworkManager(networkHandler) {

    override fun getCharacters(limit: Int, offset: Int): LiveDataStatusWrapper<GetCharactersResponseModel> {
        return doNetworkRequest(charactersApi.getCharacters(limit, offset)) {
            GetCharactersResponseMapper.transformEntityToModel(it)
        }
    }
}