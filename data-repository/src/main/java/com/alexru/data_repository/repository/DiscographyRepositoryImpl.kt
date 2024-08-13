package com.alexru.data_repository.repository

import android.database.sqlite.SQLiteFullException
import com.alexru.data_repository.data_source.local.LocalDataSource
import com.alexru.data_repository.data_source.remote.RemoteDataSource
import com.alexru.domain.model.Album
import com.alexru.domain.model.Playlist
import com.alexru.domain.model.Song
import com.alexru.domain.repository.DiscographyRepository
import com.alexru.domain.resource.DataError
import com.alexru.domain.resource.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
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
    ): Flow<Result<List<Album>, DataError.Network>> {
        return flow {
            emit(Result.Loading(true))

            val localListings = localDataSource.getAlbums(query)
            emit(
                Result.Success(
                    data = localListings
                )
            )

            val isDbEmpty = localListings.isEmpty() && query.isBlank()
            val shouldJustLoadFromCache = !isDbEmpty && !fetchFromRemote
            if(shouldJustLoadFromCache) {
                emit(Result.Loading(false))
                return@flow
            }

            val remoteListings = try {
                remoteDataSource.getAlbums()
            } catch (e: HttpException) {
                emit(
                    when(e.code()) { // TODO: DO STUFF ABOUT THIS
                        408 -> Result.Error(DataError.Network.REQUEST_TIMEOUT)
                        413 -> Result.Error(DataError.Network.PAYLOAD_TOO_LARGE)
                        else -> Result.Error(DataError.Network.UNKNOWN)
                    }
                )
                return@flow
            } catch (e: IOException) {
                emit(Result.Error(DataError.Network.NO_INTERNET))
                return@flow
            }

            localDataSource.clearAlbums()
            localDataSource.insertAlbums(
                remoteListings
            )
            emit(
                Result.Success(
                    data = localDataSource.getAlbums("")
                )
            )
            emit(Result.Loading(false))
        }
    }

    override suspend fun getAlbum(albumId: Int): Album {
        return localDataSource.getAlbum(albumId)
    }

    override suspend fun getSongs(
        albumId: Int
    ): Flow<Result<List<Song>, DataError.Network>> {
        return flow {
            emit(Result.Loading(true))

            val remoteListings = try {
                remoteDataSource.getSongs(albumId)
            } catch (e: HttpException) {
                emit(
                    when(e.code()) { // TODO: DO STUFF ABOUT THIS
                        408 -> Result.Error(DataError.Network.REQUEST_TIMEOUT)
                        413 -> Result.Error(DataError.Network.PAYLOAD_TOO_LARGE)
                        else -> Result.Error(DataError.Network.UNKNOWN)
                    }
                )
                return@flow
            } catch (e: IOException) {
                emit(Result.Error(DataError.Network.NO_INTERNET))
                return@flow
            }

            emit(
                Result.Success(
                    data = remoteListings
                )
            )
            emit(Result.Loading(false))
        }
    }

    override suspend fun getSongs(
        songs: Set<Int>
    ): Flow<Result<List<Song>, DataError.Network>> {
        return flow {
            emit(Result.Loading(true))

            val remoteListings = try {
                val res: MutableList<Song> = mutableListOf()
                songs.forEach { song ->
                    res.add(remoteDataSource.getSong(song))
                }
                res.toList()
            } catch (e: HttpException) {
                emit(
                    when(e.code()) { // TODO: DO STUFF ABOUT THIS
                        408 -> Result.Error(DataError.Network.REQUEST_TIMEOUT)
                        413 -> Result.Error(DataError.Network.PAYLOAD_TOO_LARGE)
                        else -> Result.Error(DataError.Network.UNKNOWN)
                    }
                )
                return@flow
            } catch (e: IOException) {
                emit(Result.Error(DataError.Network.NO_INTERNET))
                return@flow
            }

            emit(
                Result.Success(
                    data = remoteListings
                )
            )
            emit(Result.Loading(false))
        }
    }

    override suspend fun createPlaylist(
        playlist: Playlist
    ): Result<Boolean, DataError.Local> {
        try {
            localDataSource.createPlaylist(playlist)
            return Result.Success(true)
        } catch (e: SQLiteFullException) {
            return Result.Error(DataError.Local.DISK_FULL)
        }
    }

    override suspend fun updatePlaylistSongs(
        playlistId: Int,
        songs: Set<Int>
    ): Result<Boolean, DataError.Local> {
        try {
            localDataSource.updatePlaylistSongs(playlistId, songs)
            return Result.Success(true)
        } catch (e: SQLiteFullException) {
            return Result.Error(DataError.Local.DISK_FULL)
        }
    }

    override suspend fun deletePlaylist(playlistId: Int) {
        localDataSource.deletePlaylist(playlistId)
    }

    override suspend fun getPlaylists(query: String): List<Playlist> {
        return localDataSource.searchPlaylists(query)
    }

    override suspend fun getPlaylist(playlistId: Int): Playlist {
        return localDataSource.getPlaylist(playlistId)
    }
}