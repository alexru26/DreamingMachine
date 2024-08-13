package com.alexru.data_repository.repository

import com.alexru.data_repository.data_source.local.LocalDataSource
import com.alexru.data_repository.data_source.remote.RemoteDataSource
import com.alexru.domain.model.Album
import com.alexru.domain.model.Playlist
import com.alexru.domain.model.Resource
import com.alexru.domain.model.Song
import com.alexru.domain.repository.DiscographyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

/**
 * Implementation of [DiscographyRepository]
 */
class DiscographyRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
): DiscographyRepository {

    override suspend fun getAlbums(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Resource<List<Album>>> {
        return flow {
            emit(Resource.Loading(true))

            val localListings = localDataSource.getAlbums(query)
            emit(Resource.Success(
                data = localListings
            ))

            val isDbEmpty = localListings.isEmpty() && query.isBlank()
            val shouldJustLoadFromCache = !isDbEmpty && !fetchFromRemote
            if(shouldJustLoadFromCache) {
                emit(Resource.Loading(false))
                return@flow
            }
            val remoteListings = try {
                remoteDataSource.getAlbums()
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
                null
            }

            remoteListings?.let { listings ->
                localDataSource.clearAlbums()
                localDataSource.insertAlbums(
                    listings
                )
                emit(Resource.Success(
                    data = localDataSource.getAlbums("")
                ))
            }
            emit(Resource.Loading(false))
        }
    }

    override suspend fun getAlbum(albumId: Int): Resource<Album> {
        return try {
            Resource.Success(localDataSource.getAlbum(albumId))
        } catch (e: IOException) {
            e.printStackTrace()
            Resource.Error("Couldn't load data")
        }
    }

    override suspend fun getSongs(
        albumId: Int
    ): Flow<Resource<List<Song>>> {
        return flow {
            emit(Resource.Loading(true))

            val remoteListings = try {
                remoteDataSource.getSongs(albumId)
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
                null
            }

            remoteListings?.let { listings ->
                emit(Resource.Success(
                    data = listings
                ))
            }
            emit(Resource.Loading(false))
        }
    }

    override suspend fun getSongs(songs: Set<Int>): Flow<Resource<List<Song>>> {
        return flow {
            emit(Resource.Loading(true))

            val remoteListings = try {
                val res: MutableList<Song> = mutableListOf()
                songs.forEach { song ->
                    res.add(remoteDataSource.getSong(song))
                }
                res.toList()
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
                null
            }

            remoteListings?.let { listings ->
                emit(Resource.Success(
                    data = listings
                ))
            }
            emit(Resource.Loading(false))
        }
    }

    override suspend fun createPlaylist(playlist: Playlist) {
        localDataSource.createPlaylist(playlist)
    }

    override suspend fun updatePlaylistSongs(playlistId: Int, songs: Set<Int>) {
        localDataSource.updatePlaylistSongs(playlistId, songs)
    }

    override suspend fun deletePlaylist(playlistId: Int) {
        localDataSource.deletePlaylist(playlistId)
    }

    override suspend fun getPlaylists(query: String): Flow<Resource<List<Playlist>>> {
        return flow {
            emit(Resource.Loading(true))

            val localListings = localDataSource.searchPlaylists(query)
            emit(Resource.Success(
                data = localListings
            ))
            emit(Resource.Loading(false))
        }
    }

    override suspend fun getPlaylist(playlistId: Int): Resource<Playlist> {
        return try {
            Resource.Success(localDataSource.getPlaylist(playlistId))
        } catch (e: IOException) {
            e.printStackTrace()
            Resource.Error("Couldn't load data")
        }
    }
}