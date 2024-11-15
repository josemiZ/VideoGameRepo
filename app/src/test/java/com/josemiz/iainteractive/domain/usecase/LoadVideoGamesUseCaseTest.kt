package com.josemiz.iainteractive.domain.usecase

import com.josemiz.iainteractive.domain.repository.VideoGameRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class LoadVideoGamesUseCaseTest {

    private lateinit var loadVideoGamesUseCase: LoadVideoGamesUseCase
    private val repository = mockk<VideoGameRepository>()

    @Before
    fun setUp() {
        loadVideoGamesUseCase = LoadVideoGamesUseCase(repository)
    }

    @Test
    fun `invoke should return true when repository loads video games successfully`() = runBlocking {
        // Arrange
        coEvery { repository.loadVideoGames() } returns true

        // Act
        val result = loadVideoGamesUseCase()

        // Assert
        assertEquals(true, result)
        coVerify { repository.loadVideoGames() }
    }

    @Test
    fun `invoke should return false when repository fails to load video games`() = runBlocking {
        // Arrange
        coEvery { repository.loadVideoGames() } returns false

        // Act
        val result = loadVideoGamesUseCase()

        // Assert
        assertEquals(false, result)
        coVerify { repository.loadVideoGames() }
    }
}