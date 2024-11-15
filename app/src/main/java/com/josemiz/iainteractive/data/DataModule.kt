package com.josemiz.iainteractive.data

import android.content.Context
import androidx.room.Room
import com.josemiz.iainteractive.data.local.AppDatabase
import com.josemiz.iainteractive.data.local.VideoGameDao
import com.josemiz.iainteractive.data.remote.VideoGameService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    fun provideRetrofit() = Retrofit.Builder()
        .baseUrl("https://www.freetogame.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    fun provideVideoGameService(
        retrofit: Retrofit
    ) = retrofit.create(VideoGameService::class.java)

    @Provides
    fun provideRoomDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(context, AppDatabase::class.java, "VideoGame Database").build()

    @Provides
    fun provideVideoGameDao(
        appDatabase: AppDatabase
    ): VideoGameDao = appDatabase.videoGameDao()
}