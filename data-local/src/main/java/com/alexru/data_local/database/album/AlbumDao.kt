package com.alexru.data_local.database.album

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 * Room Dao for [AlbumEntity] related operations
 */
@Dao
interface AlbumDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlbums(albums: List<AlbumEntity>)

    @Query("DELETE FROM albumentity")
    suspend fun clearAlbums()

    @Query("SELECT * FROM albumentity WHERE id == :albumId")
    suspend fun getAlbum(albumId: Int): AlbumEntity

    @Query(
        """
        SELECT *
        FROM albumentity
        WHERE LOWER(name) LIKE '%' || LOWER(:query) || '%'
        """
    )
    suspend fun searchAlbums(query: String): List<AlbumEntity>
}