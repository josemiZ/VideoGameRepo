package com.josemiz.iainteractive.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface VideoGameDao {

    @Query("""
    SELECT * FROM VideoGame 
    WHERE is_active = 1 
    AND (:title IS NULL OR TRIM(:title) = '' OR title LIKE '%' || :title || '%') 
    ORDER BY title ASC""")
    suspend fun getVideoGames(title: String? = null): List<VideoGame>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(videoGames: List<VideoGame>): LongArray

    @Query("UPDATE VideoGame SET is_active = :isActive WHERE uid = :uid")
    suspend fun updateIsActive(uid: Int, isActive: Boolean) : Int
}