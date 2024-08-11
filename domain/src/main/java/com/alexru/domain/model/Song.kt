package com.alexru.domain.model

import kotlin.time.Duration

/**
 * Song object
 */
data class Song(
    val songId: Int,
    val albumId: Int,
    val name: String,
    val artist: String,
    val length: Duration,
    val cover: String
)
