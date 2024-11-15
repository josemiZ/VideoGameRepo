package com.josemiz.iainteractive.domain

import com.josemiz.iainteractive.domain.repository.VideoGameRepository
import com.josemiz.iainteractive.domain.usecase.DeleteVideoGameUseCase
import com.josemiz.iainteractive.domain.usecase.GetVideoGamesUseCase
import com.josemiz.iainteractive.domain.usecase.LoadVideoGamesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object DomainModule {

    @Provides
    fun provideLoadGamesUseCase(
        repository: VideoGameRepository
    ): LoadVideoGamesUseCase = LoadVideoGamesUseCase(repository)

    @Provides
    fun provideGetGamesUseCase(
        repository: VideoGameRepository
    ): GetVideoGamesUseCase = GetVideoGamesUseCase(repository)

    @Provides
    fun provideDeleteGameUseCase(
        repository: VideoGameRepository
    ): DeleteVideoGameUseCase = DeleteVideoGameUseCase(repository)
}