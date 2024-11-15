package com.josemiz.iainteractive.domain.repository

import com.josemiz.iainteractive.domain.model.VideoGameModel

interface VideoGameRepository {

    suspend fun loadVideoGames() : Boolean
    suspend fun getVideoGames(search: String?): List<VideoGameModel>
    suspend fun deleteVideoGame(id: Int): Boolean
}