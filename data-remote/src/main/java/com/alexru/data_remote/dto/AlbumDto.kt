package com.alexru.data_remote.dto

import com.squareup.moshi.Json
import com.alexru.domain.model.Album

/**
 * Dto object for [Album]
 */
data class AlbumDto(
    @field:Json(name = "albumId") val id: Int,
    @field:Json(name = "name") val name: String,
    @field:Json(name = "artist") val artist: String,
    @field:Json(name = "type") val type: String,
    @field:Json(name = "length") val length: Long,
    @field:Json(name = "release") val release: String,
    @field:Json(name = "cover") val cover: String,
)