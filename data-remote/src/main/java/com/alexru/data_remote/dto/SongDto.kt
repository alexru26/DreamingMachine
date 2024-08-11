package com.alexru.data_remote.dto

import com.squareup.moshi.Json
import com.alexru.domain.model.Song

/**
 * Dto object for [Song]
 */
data class SongDto(
    @field:Json(name = "songId") val songId: Int,
    @field:Json(name = "albumId") val albumId: Int,
    @field:Json(name = "name") val name: String,
    @field:Json(name = "artist") val artist: String,
    @field:Json(name = "length") val length: Long,
    @field:Json(name = "cover") val cover: String,
)
