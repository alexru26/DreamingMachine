package com.alexru.data_local.mapper

import com.alexru.data_local.database.album.AlbumEntity
import com.alexru.data_repository.util.toLocalDate
import com.alexru.domain.model.Album
import java.time.format.DateTimeFormatter
import kotlin.time.Duration.Companion.seconds

/**
 * Mapper to convert [AlbumEntity] to [Album]
 */
fun AlbumEntity.toAlbumListing(): Album {
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
 * Mapper to convert [Album] to [AlbumEntity]
 */
fun Album.toAlbumListingEntity(): AlbumEntity {
    return AlbumEntity(
        id = id,
        name = name,
        artist = artist,
        type = type,
        length = length.inWholeSeconds,
        release = release.format(DateTimeFormatter.ofPattern("MM-dd-yyyy")),
        cover = cover
    )
}