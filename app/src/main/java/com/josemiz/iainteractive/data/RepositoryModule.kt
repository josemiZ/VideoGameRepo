package com.josemiz.iainteractive.data

import com.josemiz.iainteractive.data.repository.VideoGameRepositoryImpl
import com.josemiz.iainteractive.domain.repository.VideoGameRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindVideoGameRepository(
        videoGameRepositoryImpl: VideoGameRepositoryImpl
    ): VideoGameRepository
}