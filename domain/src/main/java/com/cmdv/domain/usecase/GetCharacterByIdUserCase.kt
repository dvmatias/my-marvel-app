package com.cmdv.domain.usecase

import com.cmdv.domain.base.BaseUseCase
import com.cmdv.domain.model.CharacterModel
import com.cmdv.domain.repository.CharacterRepository
import com.cmdv.domain.utils.LiveDataStatusWrapper
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class GetCharacterByIdUserCase(
    private val characterRepository: CharacterRepository
) : BaseUseCase<LiveDataStatusWrapper<CharacterModel>, GetCharacterByIdUserCase.Params>() {

    override suspend fun run(params: Params): LiveDataStatusWrapper<CharacterModel> =
        characterRepository.fetchCharacterById(params.characterId)

    data class Params(val characterId: Int)

}