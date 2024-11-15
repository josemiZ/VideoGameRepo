package com.josemiz.iainteractive.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.josemiz.iainteractive.domain.usecase.DeleteVideoGameUseCase
import com.josemiz.iainteractive.domain.usecase.GetVideoGamesUseCase
import com.josemiz.iainteractive.domain.usecase.LoadVideoGamesUseCase
import com.josemiz.iainteractive.ui.uimodel.VideoGameUi
import com.josemiz.iainteractive.ui.uimodel.VideoGameUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val loadVideoGamesUseCase: LoadVideoGamesUseCase,
    private val getVideoGamesUseCase: GetVideoGamesUseCase,
    private val deleteVideoGameUseCase: DeleteVideoGameUseCase
): ViewModel() {

    private val _uiState = MutableStateFlow(VideoGameUiState())
    val uiState: StateFlow<VideoGameUiState> = _uiState.asStateFlow()

    private var fetchJob: Job? = null

    fun resetAreVideoGamesSaved() {
        _uiState.value = _uiState.value.copy(areVideoGamesSaved = false)
    }

    fun loadGames() {
        fetchJob {
            _uiState.update { it.copy(loading = true) }
            val isSuccessState = loadVideoGamesUseCase.invoke()
            _uiState.update { it.copy(loading = false, areVideoGamesSaved = isSuccessState) }
        }
    }

    fun getGames(search: String? = null) {
        fetchJob {
            _uiState.update { it.copy(loading = true) }
            val list = getVideoGamesUseCase.invoke(search).map {
                VideoGameUi(
                    id = it.id,
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
            _uiState.update { it.copy(loading = false, videoGames = list) }
        }
    }

    fun deleteGame(id: Int) {
        fetchJob {
            _uiState.update { it.copy(loading = true) }
            val delete = deleteVideoGameUseCase.invoke(id)
            _uiState.update { it.copy(loading = false, deleteGame = delete) }
        }
    }

    private fun fetchJob(job: suspend () -> Unit) {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            try {
                job.invoke()
            } catch (io: IOException) {
                _uiState.update {
                    it.copy(errorMessage = io.message)
                }
            }
        }
    }
}