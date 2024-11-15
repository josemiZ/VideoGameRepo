package com.josemiz.iainteractive.domain.usecase

import com.josemiz.iainteractive.domain.repository.VideoGameRepository
import javax.inject.Inject

class DeleteVideoGameUseCase @Inject constructor(
    private val repository: VideoGameRepository
) {

    suspend operator fun invoke(id: Int): Boolean {
        return repository.deleteVideoGame(id)
    }
}