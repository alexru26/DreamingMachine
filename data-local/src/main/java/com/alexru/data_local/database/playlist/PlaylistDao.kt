package com.alexru.data_local.database.playlist

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

/**
 * Room Dao for [PlaylistEntity] related operations
 */
@Dao
interface PlaylistDao {

    @Insert
    suspend fun createPlaylist(playlist: PlaylistEntity)

    @Query("UPDATE playlistentity SET songs = :songs WHERE id = :playlistId")
    suspend fun updatePlaylistSongs(playlistId: Int, songs: Set<Int>)

    @Query("DELETE FROM playlistentity WHERE id = :playlistId")
    suspend fun deletePlaylist(playlistId: Int)

    @Query("SELECT * FROM playlistentity WHERE id == :playlistId")
    suspend fun getPlaylist(playlistId: Int): PlaylistEntity

    @Query(
        """
        SELECT *
        FROM playlistentity
        WHERE LOWER(name) LIKE '%' || LOWER(:query) || '%'
        """
    )
    suspend fun searchPlaylists(query: String): List<PlaylistEntity>

}