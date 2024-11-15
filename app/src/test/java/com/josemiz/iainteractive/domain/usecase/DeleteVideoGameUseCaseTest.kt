package com.josemiz.iainteractive.domain.usecase

import com.josemiz.iainteractive.domain.repository.VideoGameRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class DeleteVideoGameUseCaseTest {

    private lateinit var deleteVideoGameUseCase: DeleteVideoGameUseCase
    private val repository = mockk<VideoGameRepository>()

    @Before
    fun setUp() {
        deleteVideoGameUseCase = DeleteVideoGameUseCase(repository)
    }

    @Test
    fun `invoke should return true when repository deletes video game successfully`() = runBlocking {
        // Arrange
        val videoGameId = 1
        coEvery { repository.deleteVideoGame(videoGameId) } returns true

        // Act
        val result = deleteVideoGameUseCase(videoGameId)

        // Assert
        assertEquals(true, result)
        coVerify { repository.deleteVideoGame(videoGameId) }
    }

    @Test
    fun `invoke should return false when repository fails to delete video game`() = runBlocking {
        // Arrange
        val videoGameId = 1
        coEvery { repository.deleteVideoGame(videoGameId) } returns false

        // Act
        val result = deleteVideoGameUseCase(videoGameId)

        // Assert
        assertEquals(false, result)
        coVerify { repository.deleteVideoGame(videoGameId) }
    }
}