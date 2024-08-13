package com.alexru.domain.model

/**
 * Playlist object
 */
data class Playlist(
    val id: Int = 0,
    val name: String,
    val songs: Set<Int>
)
