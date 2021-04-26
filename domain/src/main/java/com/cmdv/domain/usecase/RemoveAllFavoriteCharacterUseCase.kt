package com.cmdv.domain.usecase

import com.cmdv.domain.base.BaseUseCase
import com.cmdv.domain.model.CharacterModel
import com.cmdv.domain.repository.CharacterRepository
import com.cmdv.domain.repository.FavoriteCharacterRepository
import com.cmdv.domain.utils.Event
import com.cmdv.domain.utils.LiveDataStatusWrapper
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class RemoveAllFavoriteCharacterUseCase(
    private val favoriteCharacterRepository: FavoriteCharacterRepository
) : BaseUseCase<LiveDataStatusWrapper<Event<Int>>, RemoveAllFavoriteCharacterUseCase.Params>() {

    @ExperimentalCoroutinesApi
    override suspend fun run(params: Params): LiveDataStatusWrapper<Event<Int>> =
        favoriteCharacterRepository.removeAll()

    class Params
}