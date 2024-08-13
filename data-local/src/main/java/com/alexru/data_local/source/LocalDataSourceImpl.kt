package com.alexru.data_local.source

import com.alexru.data_local.database.album.AlbumDao
import com.alexru.data_local.database.playlist.PlaylistDao
import com.alexru.data_local.mapper.toAlbumListing
import com.alexru.data_local.mapper.toAlbumListingEntity
import com.alexru.data_local.mapper.toPlaylist
import com.alexru.data_local.mapper.toPlaylistEntity
import com.alexru.data_repository.data_source.local.LocalDataSource
import com.alexru.domain.model.Album
import com.alexru.domain.model.Playlist
import javax.inject.Inject

/**
 * Implementation of [LocalDataSource]
 */
class LocalDataSourceImpl @Inject constructor(
    private val albumDao: AlbumDao,
    private val playlistDao: PlaylistDao
): LocalDataSource {

    override suspend fun getAlbums(query: String): List<Album> {
        return albumDao.searchAlbums(query)
            .map {
                it.toAlbumListing()
            }
    }

    override suspend fun getAlbum(albumId: Int): Album {
        return albumDao.getAlbum(albumId).toAlbumListing()
    }

    override suspend fun insertAlbums(albums: List<Album>) {
        albumDao.insertAlbums(albums.map { it.toAlbumListingEntity() })
    }

    override suspend fun clearAlbums() {
        albumDao.clearAlbums()
    }

    override suspend fun createPlaylist(playlist: Playlist) {
        playlistDao.createPlaylist(playlist.toPlaylistEntity())
    }

    override suspend fun updatePlaylistSongs(playlistId: Int, songs: Set<Int>) {
        playlistDao.updatePlaylistSongs(playlistId, songs)
    }

    override suspend fun deletePlaylist(playlistId: Int) {
        playlistDao.deletePlaylist(playlistId)
    }

    override suspend fun getPlaylist(playlistId: Int): Playlist {
        return playlistDao.getPlaylist(playlistId).toPlaylist()
    }

    override suspend fun searchPlaylists(query: String): List<Playlist> {
        return playlistDao.searchPlaylists(query).map {
            it.toPlaylist()
        }
    }
}