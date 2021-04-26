package com.cmdv.domain.usecase

import com.cmdv.domain.base.BaseUseCase
import com.cmdv.domain.model.CharacterModel
import com.cmdv.domain.repository.FavoriteCharacterRepository
import com.cmdv.domain.utils.LiveDataStatusWrapper
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class GetFavoriteCharactersUseCase(
    private val favoriteCharacterRepository: FavoriteCharacterRepository
) : BaseUseCase<LiveDataStatusWrapper<List<CharacterModel>>, GetFavoriteCharactersUseCase.Params>() {

    override suspend fun run(params: Params): LiveDataStatusWrapper<List<CharacterModel>> =
        favoriteCharacterRepository.getFavorites()

    class Params
}
