package com.alexru.domain.repository

import com.alexru.domain.model.Album
import com.alexru.domain.model.Playlist
import com.alexru.domain.model.Song
import com.alexru.domain.resource.DataError
import com.alexru.domain.resource.Result
import kotlinx.coroutines.flow.Flow

/**
 * DiscographyRepository interface
 */
interface DiscographyRepository {

    suspend fun getAlbums(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Result<List<Album>, DataError.Network>>

    suspend fun getAlbum(
        albumId: Int
    ): Album

    suspend fun getSongs(
        // fetchFromRemote: Boolean,
        albumId: Int
    ): Flow<Result<List<Song>, DataError.Network>>

    suspend fun getSongs(
        songs: Set<Int>
    ): Flow<Result<List<Song>, DataError.Network>>

    suspend fun createPlaylist(
        playlist: Playlist
    ): Result<Boolean, DataError.Local>

    suspend fun updatePlaylistSongs(
        playlistId: Int,
        songs: Set<Int>
    ): Result<Boolean, DataError.Local>

    suspend fun deletePlaylist(
        playlistId: Int
    )

    suspend fun getPlaylists(
        query: String
    ): List<Playlist>

    suspend fun getPlaylist(
        playlistId: Int
    ): Playlist

}