package com.alexru.data_local.database.playlist

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.alexru.domain.model.Playlist

/**
 * Room Entity for [Playlist]
 */
@Entity
@TypeConverters(SongsListConverter::class)
data class PlaylistEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val songs: List<Int>
)
