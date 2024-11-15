package com.josemiz.iainteractive.data.repository

import com.josemiz.iainteractive.common.IoDispatcher
import com.josemiz.iainteractive.data.local.VideoGame
import com.josemiz.iainteractive.data.local.VideoGameDao
import com.josemiz.iainteractive.data.remote.VideoGameService
import com.josemiz.iainteractive.domain.model.VideoGameModel
import com.josemiz.iainteractive.domain.repository.VideoGameRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class VideoGameRepositoryImpl @Inject constructor(
    private val videoGameService: VideoGameService,
    private val videoGameDao: VideoGameDao,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
): VideoGameRepository {

    override suspend fun loadVideoGames(): Boolean = withContext(dispatcher){
        val videoGames = videoGameDao.getVideoGames()
        if (videoGames.isNotEmpty()) return@withContext true
        val list = videoGameService.getVideoGames()
        val array = videoGameDao.insertAll(list.map {
            VideoGame(
                uid = it.id,
                title = it.title,
                shortDescription = it.shortDescription,
                thumbnail = it.thumbnail,
                gameUrl = it.gameUrl,
                genre = it.genre,
                platform = it.platform,
                publisher = it.publisher,
                developer = it.developer,
                releaseDate = it.releaseDate,
                profileUrl = it.profileUrl,
                isActive = true
            )
        })
        return@withContext array.isNotEmpty()
    }

    override suspend fun getVideoGames(search: String?): List<VideoGameModel> = withContext(dispatcher) {
        return@withContext videoGameDao.getVideoGames(search).map {
            VideoGameModel(
                id = it.uid,
                title = it.title,
                thumbnail = it.thumbnail,
                shortDescription = it.shortDescription,
                gameUrl = it.gameUrl,
                genre = it.genre,
                platform = it.platform,
                publisher = it.publisher,
                developer = it.developer,
                releaseDate = it.releaseDate,
                profileUrl = it.profileUrl
            )
        }
    }

    override suspend fun deleteVideoGame(id: Int): Boolean = withContext(dispatcher) {
        return@withContext videoGameDao.updateIsActive(id, false) > 0
    }
}