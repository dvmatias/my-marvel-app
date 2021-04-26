package com.cmdv.domain.usecase

import com.cmdv.domain.base.BaseUseCase
import com.cmdv.domain.model.CharacterModel
import com.cmdv.domain.repository.CharacterRepository
import com.cmdv.domain.repository.FavoriteCharacterRepository
import com.cmdv.domain.utils.Event
import com.cmdv.domain.utils.LiveDataStatusWrapper
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class RemoveFavoriteCharacterUseCase(
    private val favoriteCharacterRepository: FavoriteCharacterRepository
) : BaseUseCase<LiveDataStatusWrapper<Event<Int>>, RemoveFavoriteCharacterUseCase.Params>() {

    @ExperimentalCoroutinesApi
    override suspend fun run(params: Params): LiveDataStatusWrapper<Event<Int>> =
        favoriteCharacterRepository.removeFavorite(params.character, params.position)

    data class Params(val character: CharacterModel, val position: Int)
}