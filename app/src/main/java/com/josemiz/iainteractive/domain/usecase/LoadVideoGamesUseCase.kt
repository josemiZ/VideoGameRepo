package com.josemiz.iainteractive.domain.usecase

import com.josemiz.iainteractive.domain.repository.VideoGameRepository
import javax.inject.Inject

class LoadVideoGamesUseCase @Inject constructor(
    private val repository: VideoGameRepository
) {

    suspend operator fun invoke(): Boolean {
        return repository.loadVideoGames()
    }
}