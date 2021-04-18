package com.cmdv.domain.usecase

import com.cmdv.domain.base.BaseUseCase
import com.cmdv.domain.model.CharacterModel
import com.cmdv.domain.repository.CharactersRepository
import com.cmdv.domain.utils.LiveDataStatusWrapper
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class AddFavouriteCharacterUseCase(
    private val charactersRepository: CharactersRepository
) : BaseUseCase<LiveDataStatusWrapper<Int>, AddFavouriteCharacterUseCase.Params>() {

    @ExperimentalCoroutinesApi
    override suspend fun run(params: Params): LiveDataStatusWrapper<Int> =
        charactersRepository.addFavourite(params.character, params.position)

    data class Params(val character: CharacterModel, val position: Int)
}