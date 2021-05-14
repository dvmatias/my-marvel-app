package com.cmdv.domain.usecase

import com.cmdv.domain.base.BaseUseCase
import com.cmdv.domain.model.CharacterModel
import com.cmdv.domain.repository.CharacterRepository
import com.cmdv.domain.utils.LiveDataStatusWrapper
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class GetCharactersUseCase(
    private val characterRepository: CharacterRepository
) : BaseUseCase<LiveDataStatusWrapper<List<CharacterModel>>, GetCharactersUseCase.Params>() {

    override suspend fun run(params: Params): LiveDataStatusWrapper<List<CharacterModel>> =
        characterRepository.getCharacters(params.fetch, params.limit, params.offset)

    data class Params(val fetch: Boolean, val limit: Int, val offset: Int)
}