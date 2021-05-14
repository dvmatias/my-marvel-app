package com.cmdv.data.repository

import com.cmdv.data.mapper.CharacterRoomMapper
import com.cmdv.data.mapper.GetCharactersResponseMapper
import com.cmdv.data.source.dao.CharactersDao
import com.cmdv.data.source.dao.FavoriteCharactersDao
import com.cmdv.data.source.service.CharactersApi
import com.cmdv.data.utils.NetworkHandler
import com.cmdv.data.utils.NetworkManager
import com.cmdv.domain.model.CharacterModel
import com.cmdv.domain.repository.CharacterRepository
import com.cmdv.domain.utils.LiveDataStatusWrapper
import kotlinx.coroutines.runBlocking

class CharacterRepositoryImpl(
    private val charactersApi: CharactersApi,
    private val charactersDao: CharactersDao,
    private val favoriteCharactersDao: FavoriteCharactersDao,
    networkHandler: NetworkHandler
) : CharacterRepository, NetworkManager(networkHandler) {

    override fun getTotalCharacters(): LiveDataStatusWrapper<Int> {
        return doNetworkRequest(charactersApi.getCharacters(1, 0)) { response ->
            response.data?.total ?: 0
        }
    }

    override fun getCharacters(
        fetch: Boolean,
        limit: Int,
        offset: Int
    ): LiveDataStatusWrapper<List<CharacterModel>> = runBlocking {
        val storedCharacters = getAll()

        if (fetch || storedCharacters.isEmpty()) {
            fetchCharacters(limit, offset).let {
                it.data?.let { data -> store(data) }
                it
            }
        } else {
            LiveDataStatusWrapper.success(storedCharacters)
        }
    }

    private fun fetchCharacters(limit: Int, offset: Int): LiveDataStatusWrapper<ArrayList<CharacterModel>> =
        doNetworkRequest(charactersApi.getCharacters(limit, offset)) { response ->
            GetCharactersResponseMapper.transformEntityToModel(response).characters
        }


    private fun getAll(): List<CharacterModel> =
        charactersDao.getAll().map {
            CharacterRoomMapper.transformEntityToModel(it).apply {
                isFavourite = favoriteCharactersDao.getById(id) != null
            }
        }

    private fun store(characters: List<CharacterModel>) {
        characters.map {
            CharacterRoomMapper.transformModelToEntity(it).apply {
                isFavorite = favoriteCharactersDao.getById(characterId) != null
            }
        }.also {
            charactersDao.insert(it)
        }
    }
}