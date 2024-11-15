package com.josemiz.iainteractive.data.repository

import com.josemiz.iainteractive.data.local.VideoGame
import com.josemiz.iainteractive.data.local.VideoGameDao
import com.josemiz.iainteractive.data.remote.VideoGameEntity
import com.josemiz.iainteractive.data.remote.VideoGameService
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class VideoGameRepositoryImplTest {

    private lateinit var videoGameRepository: VideoGameRepositoryImpl
    private val videoGameService = mockk<VideoGameService>()
    private val videoGameDao = mockk<VideoGameDao>()
    private val dispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        videoGameRepository = VideoGameRepositoryImpl(videoGameService, videoGameDao, dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `loadVideoGames should return true when video games are already in local database`() =
        runTest {
            // Arrange
            coEvery { videoGameDao.getVideoGames() } returns listOf(
                VideoGame(
                    uid = 1,
                    title = "Test Game",
                    thumbnail = "",
                    shortDescription = "",
                    gameUrl = "",
                    genre = "",
                    platform = "",
                    publisher = "",
                    developer = "",
                    releaseDate = "",
                    profileUrl = "",
                    isActive = true
                )
            )

            // Act
            val result = videoGameRepository.loadVideoGames()

            // Assert
            assertTrue(result)
            coVerify(exactly = 0) { videoGameService.getVideoGames() }
        }

    @Test
    fun `loadVideoGames should fetch from service and insert into database when local database is empty`() =
        runTest {
            // Arrange
            coEvery { videoGameDao.getVideoGames() } returns emptyList()
            val serviceList = listOf(
                VideoGameEntity(
                    id = 1,
                    title = "Test Game",
                    shortDescription = "Test",
                    thumbnail = "url",
                    gameUrl = "url",
                    genre = "RPG",
                    platform = "PC",
                    publisher = "Publisher",
                    developer = "Dev",
                    releaseDate = "2022-01-01",
                    profileUrl = "profile_url"
                )
            )
            coEvery { videoGameService.getVideoGames() } returns serviceList
            coEvery { videoGameDao.insertAll(any()) } returns LongArray(1)

            // Act
            val result = videoGameRepository.loadVideoGames()

            // Assert
            assertTrue(result)
            coVerify { videoGameService.getVideoGames() }
            coVerify { videoGameDao.insertAll(any()) }
        }

    @Test
    fun `getVideoGames should return a list of VideoGameModel based on search criteria`() =
        runTest {
            // Arrange
            val search = "Test"
            val videoGameList = listOf(
                VideoGame(
                    uid = 1,
                    title = "Test Game",
                    isActive = true,
                    thumbnail = "url",
                    shortDescription = "desc",
                    gameUrl = "gameUrl",
                    genre = "RPG",
                    platform = "PC",
                    publisher = "Publisher",
                    developer = "Dev",
                    releaseDate = "2022-01-01",
                    profileUrl = "profile_url"
                )
            )
            coEvery { videoGameDao.getVideoGames(search) } returns videoGameList

            // Act
            val result = videoGameRepository.getVideoGames(search)

            // Assert
            assertEquals(1, result.size)
            assertEquals("Test Game", result[0].title)
            coVerify { videoGameDao.getVideoGames(search) }
        }

    @Test
    fun `deleteVideoGame should return true when a video game is successfully updated`() = runTest {
        // Arrange
        val videoGameId = 1
        coEvery { videoGameDao.updateIsActive(videoGameId, false) } returns 1

        // Act
        val result = videoGameRepository.deleteVideoGame(videoGameId)

        // Assert
        assertTrue(result)
        coVerify { videoGameDao.updateIsActive(videoGameId, false) }
    }

    @Test
    fun `deleteVideoGame should return false when no video game is updated`() = runTest {
        // Arrange
        val videoGameId = 1
        coEvery { videoGameDao.updateIsActive(videoGameId, false) } returns 0

        // Act
        val result = videoGameRepository.deleteVideoGame(videoGameId)

        // Assert
        assertTrue(!result)
        coVerify { videoGameDao.updateIsActive(videoGameId, false) }
    }
}