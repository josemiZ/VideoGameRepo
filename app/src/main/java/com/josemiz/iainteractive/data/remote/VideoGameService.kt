package com.josemiz.iainteractive.data.remote

import retrofit2.http.GET

interface VideoGameService {

    @GET("api/games")
    suspend fun getVideoGames(): List<VideoGameEntity>
}