package com.alexru.data_local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.alexru.data_local.database.album.AlbumDao
import com.alexru.data_local.database.album.AlbumEntity
import com.alexru.data_local.database.playlist.PlaylistDao
import com.alexru.data_local.database.playlist.PlaylistEntity
import com.alexru.data_local.database.playlist.SongsListConverter

/**
 * Room Database
 */
@Database(
    entities = [
        AlbumEntity::class,
        PlaylistEntity::class
    ],
    version = 1
)
@TypeConverters(SongsListConverter::class)
abstract class DiscographyDatabase: RoomDatabase() {

    abstract val albumDao: AlbumDao

    abstract val playlistDao: PlaylistDao

}