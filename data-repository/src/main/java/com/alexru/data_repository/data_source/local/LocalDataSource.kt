package com.alexru.data_repository.data_source.local

import com.alexru.domain.model.Album
import com.alexru.domain.model.Playlist

/**
 * LocalDataSource interface
 */
interface LocalDataSource {

    suspend fun getAlbums(query: String): List<Album>

    suspend fun getAlbum(albumId: Int): Album

    suspend fun insertAlbums(albums: List<Album>)

    suspend fun clearAlbums()

    suspend fun createPlaylist(playlist: Playlist)

    suspend fun updatePlaylistSongs(playlistId: Int, songs: Set<Int>)

    suspend fun deletePlaylist(playlistId: Int)

    suspend fun getPlaylist(playlistId: Int): Playlist

    suspend fun searchPlaylists(query: String): List<Playlist>
}