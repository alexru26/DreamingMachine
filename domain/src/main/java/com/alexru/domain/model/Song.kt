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
    val cover: String,
    val audio: ByteArray = byteArrayOf()
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Song

        if (songId != other.songId) return false
        if (albumId != other.albumId) return false
        if (name != other.name) return false
        if (artist != other.artist) return false
        if (length != other.length) return false
        if (cover != other.cover) return false
        if (!audio.contentEquals(other.audio)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = songId
        result = 31 * result + albumId
        result = 31 * result + name.hashCode()
        result = 31 * result + artist.hashCode()
        result = 31 * result + length.hashCode()
        result = 31 * result + cover.hashCode()
        result = 31 * result + audio.contentHashCode()
        return result
    }
}
