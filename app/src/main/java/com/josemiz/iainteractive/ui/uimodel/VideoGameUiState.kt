package com.josemiz.iainteractive.ui.uimodel

data class VideoGameUiState(
    val loading: Boolean = false,
    val areVideoGamesSaved: Boolean = false,
    val videoGames: List<VideoGameUi> = emptyList(),
    val deleteGame: Boolean = false,
    val errorMessage: String? = null
)