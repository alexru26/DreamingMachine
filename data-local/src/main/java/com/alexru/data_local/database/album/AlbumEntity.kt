package com.alexru.data_local.database.album

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.alexru.domain.model.Album

/**
 * Room Entity for [Album]
 */
@Entity
data class AlbumEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val artist: String,
    val type: String,
    val length: Long,
    val release: String,
    val cover: String
)
