package com.cmdv.domain.usecase

import com.cmdv.domain.base.BaseUseCase
import com.cmdv.domain.repository.CharactersRepository
import com.cmdv.domain.utils.LiveDataStatusWrapper
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class RemoveFavouriteCharacterUseCase(
    private val charactersRepository: CharactersRepository
) : BaseUseCase<LiveDataStatusWrapper<Boolean>, RemoveFavouriteCharacterUseCase.Params>() {

    @ExperimentalCoroutinesApi
    override suspend fun run(params: Params): LiveDataStatusWrapper<Boolean> =
        charactersRepository.removeFavourite(params.characterId)

    data class Params(val characterId: Int)
}