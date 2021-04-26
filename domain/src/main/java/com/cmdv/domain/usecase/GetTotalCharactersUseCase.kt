package com.cmdv.domain.usecase

import com.cmdv.domain.base.BaseUseCase
import com.cmdv.domain.repository.CharacterRepository
import com.cmdv.domain.utils.LiveDataStatusWrapper
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class GetTotalCharactersUseCase(
    private val characterRepository: CharacterRepository
) : BaseUseCase<LiveDataStatusWrapper<Int>, GetTotalCharactersUseCase.Params>() {

    override suspend fun run(params: Params): LiveDataStatusWrapper<Int> =
        characterRepository.getTotalCharacters()

    class Params
}