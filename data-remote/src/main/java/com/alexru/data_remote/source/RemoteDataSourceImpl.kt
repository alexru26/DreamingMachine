package com.alexru.data_remote.source

import com.alexru.data_remote.util.toAlbumListing
import com.alexru.data_remote.util.toSong
import com.alexru.data_remote.networking.DiscographyApi
import com.alexru.data_repository.data_source.remote.RemoteDataSource
import com.alexru.domain.model.Album
import com.alexru.domain.model.Song
import javax.inject.Inject

/**
 * Implementation of [RemoteDataSource]
 */
class RemoteDataSourceImpl @Inject constructor(
    private val api: DiscographyApi
): RemoteDataSource {

    override suspend fun getAlbums(): List<Album> {
        return api.getAlbums()
            .map {
                it.toAlbumListing()
            }
    }

    override suspend fun getAlbum(
        albumId: Int
    ): Album {
        return api.getAlbum(albumId).toAlbumListing()
    }

    override suspend fun getSongs(albumId: Int): List<Song> {
        return api.getSongs(albumId)
            .map {
                it.toSong()
            }
    }

    override suspend fun getSong(songId: Int): Song {
        return api.getSong(songId).toSong()
    }

    override suspend fun downloadSong(songId: Int): ByteArray {
        val response = api.downloadSong(songId)
        return response.bytes()
    }
}