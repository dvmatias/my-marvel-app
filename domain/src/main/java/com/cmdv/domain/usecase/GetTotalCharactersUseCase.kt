package com.cmdv.domain.usecase

import com.cmdv.domain.base.BaseUseCase
import com.cmdv.domain.model.GetCharactersResponseModel
import com.cmdv.domain.repository.CharactersRepository
import com.cmdv.domain.utils.LiveDataStatusWrapper
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class GetTotalCharactersUseCase(
    private val charactersRepository: CharactersRepository
) : BaseUseCase<LiveDataStatusWrapper<Int>, GetTotalCharactersUseCase.Params>() {

    override suspend fun run(params: Params): LiveDataStatusWrapper<Int> =
        charactersRepository.getTotalCharacters()

    class Params
}