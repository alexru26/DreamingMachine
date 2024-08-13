package com.alexru.domain.repository

import com.alexru.domain.model.Album
import com.alexru.domain.model.Playlist
import com.alexru.domain.model.Resource
import com.alexru.domain.model.Song
import kotlinx.coroutines.flow.Flow

/**
 * DiscographyRepository interface
 */
interface DiscographyRepository {

    suspend fun getAlbums(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Resource<List<Album>>>

    suspend fun getAlbum(
        albumId: Int
    ): Resource<Album>

    suspend fun getSongs(
        // fetchFromRemote: Boolean,
        albumId: Int
    ): Flow<Resource<List<Song>>>

    suspend fun getSongs(
        songs: Set<Int>
    ): Flow<Resource<List<Song>>>

    suspend fun createPlaylist(
        playlist: Playlist
    )

    suspend fun updatePlaylistSongs(
        playlistId: Int,
        songs: Set<Int>
    )

    suspend fun deletePlaylist(
        playlistId: Int
    )

    suspend fun getPlaylists(
        query: String
    ): Flow<Resource<List<Playlist>>>

    suspend fun getPlaylist(
        playlistId: Int
    ): Resource<Playlist>

}