package com.josemiz.iainteractive.domain.usecase

import com.josemiz.iainteractive.domain.model.VideoGameModel
import com.josemiz.iainteractive.domain.repository.VideoGameRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetVideoGamesUseCaseTest {

    private lateinit var getVideoGamesUseCase: GetVideoGamesUseCase
    private val repository = mockk<VideoGameRepository>()

    @Before
    fun setUp() {
        getVideoGamesUseCase = GetVideoGamesUseCase(repository)
    }

    @Test
    fun `invoke should return list of video games when search is not null`() = runBlocking {
        // Arrange
        val searchQuery = "Test"
        val expectedGames = listOf(
            VideoGameModel(
                id = 1,
                title = "Test Game 1",
                thumbnail = "url1",
                shortDescription = "Description 1",
                gameUrl = "gameUrl1",
                genre = "Action",
                platform = "PC",
                publisher = "Publisher 1",
                developer = "Developer 1",
                releaseDate = "2022-01-01",
                profileUrl = "profile_url_1"
            ),
            VideoGameModel(
                id = 2,
                title = "Test Game 2",
                thumbnail = "url2",
                shortDescription = "Description 2",
                gameUrl = "gameUrl2",
                genre = "Adventure",
                platform = "Console",
                publisher = "Publisher 2",
                developer = "Developer 2",
                releaseDate = "2022-02-02",
                profileUrl = "profile_url_2"
            )
        )
        coEvery { repository.getVideoGames(searchQuery) } returns expectedGames

        // Act
        val result = getVideoGamesUseCase(searchQuery)

        // Assert
        assertEquals(expectedGames, result)
        coVerify { repository.getVideoGames(searchQuery) }
    }

    @Test
    fun `invoke should return empty list when search is null`() = runBlocking {
        // Arrange
        val searchQuery: String? = null
        val expectedGames = emptyList<VideoGameModel>()
        coEvery { repository.getVideoGames(searchQuery) } returns expectedGames

        // Act
        val result = getVideoGamesUseCase(searchQuery)

        // Assert
        assertEquals(expectedGames, result)
        coVerify { repository.getVideoGames(searchQuery) }
    }
}