package com.josemiz.iainteractive.ui.navigation

import kotlinx.serialization.Serializable

object VideoGameNavigation {
    @Serializable
    object Splash

    @Serializable
    object MainSearch

    @Serializable
    data class Detail(
        val id: Int,
        val title: String,
        val thumbnail: String,
        val shortDescription: String,
        val gameUrl: String,
        val genre: String,
        val platform: String,
        val publisher: String,
        val developer: String,
        val releaseDate: String,
        val profileUrl: String
    )
}