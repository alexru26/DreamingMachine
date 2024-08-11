package com.alexru.data_remote.mapper

import com.alexru.data_remote.dto.AlbumDto
import com.alexru.data_remote.dto.SongDto
import com.alexru.data_repository.util.toLocalDate
import com.alexru.domain.model.Album
import com.alexru.domain.model.Song
import kotlin.time.Duration.Companion.seconds

/**
 * Mapper to convert [AlbumDto] to [Album]
 */
fun AlbumDto.toAlbumListing(): Album {
    return Album(
        id = id,
        name = name,
        artist = artist,
        type = type,
        length = length.seconds,
        release = release.toLocalDate(),
        cover = cover
    )
}

/**
 * Mapper to convert [SongDto] to [Song]
 */
fun SongDto.toSong(): Song {
    return Song(
        songId = songId,
        albumId = albumId,
        name = name,
        artist = artist,
        length = length.seconds,
        cover = cover
    )
}