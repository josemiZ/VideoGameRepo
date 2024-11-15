package com.josemiz.iainteractive.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class VideoGame(
    @PrimaryKey val uid: Int,
    @ColumnInfo("title") val title: String,
    @ColumnInfo("thumbnail") val thumbnail: String,
    @ColumnInfo("short_description") val shortDescription: String,
    @ColumnInfo("game_url") val gameUrl: String,
    @ColumnInfo("genre") val genre: String,
    @ColumnInfo("platform") val platform: String,
    @ColumnInfo("publisher") val publisher: String,
    @ColumnInfo("developer") val developer: String,
    @ColumnInfo("release_date") val releaseDate: String,
    @ColumnInfo("profile_url") val profileUrl: String,
    @ColumnInfo("is_active") val isActive: Boolean,
)