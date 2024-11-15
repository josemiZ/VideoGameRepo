package com.josemiz.iainteractive.domain.usecase

import com.josemiz.iainteractive.domain.model.VideoGameModel
import com.josemiz.iainteractive.domain.repository.VideoGameRepository
import javax.inject.Inject

class GetVideoGamesUseCase @Inject constructor(
    private val repository: VideoGameRepository
) {

    suspend operator fun invoke(search: String?): List<VideoGameModel> {
        return repository.getVideoGames(search)
    }
}