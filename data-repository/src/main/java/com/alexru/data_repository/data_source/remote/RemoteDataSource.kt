package com.alexru.data_repository.data_source.remote

import com.alexru.domain.model.Album
import com.alexru.domain.model.Song

/**
 * RemoteDataSource interface
 */
interface RemoteDataSource {

    suspend fun getAlbums(): List<Album>

    suspend fun getAlbum(albumId: Int): Album

    suspend fun getSongs(albumId: Int): List<Song>

    suspend fun getSong(songId: Int): Song

    suspend fun downloadSong(songId: Int): ByteArray
}