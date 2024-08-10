package com.alexru.data_local.mapper

import com.alexru.data_local.database.playlist.PlaylistEntity
import com.alexru.domain.model.Playlist

/**
 * Mapper to convert [PlaylistEntity] to [Playlist]
 */
fun PlaylistEntity.toPlaylist(): Playlist {
    return Playlist(
        id = id,
        name = name,
        songs = songs
    )
}

/**
 * Mapper to convert [Playlist] to [PlaylistEntity]
 */
fun Playlist.toPlaylistEntity(): PlaylistEntity {
    return PlaylistEntity(
        name = name,
        songs = songs
    )
}