package com.josemiz.iainteractive.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [VideoGame::class], version = 1)
abstract class AppDatabase: RoomDatabase() {

    abstract fun videoGameDao(): VideoGameDao
}