package com.alexru.data_local.di

import android.app.Application
import androidx.room.Room
import com.alexru.data_local.database.album.AlbumDao
import com.alexru.data_local.database.DiscographyDatabase
import com.alexru.data_local.database.playlist.PlaylistDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * DI Module that provides Database and Dao's
 */
@Module
@InstallIn(SingletonComponent::class)
class PersistenceModule {

    @Provides
    @Singleton
    fun provideDiscographyDatabase(app: Application): DiscographyDatabase {
        return Room.databaseBuilder(
            app,
            DiscographyDatabase::class.java,
            "discography.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideAlbumDao(discographyDatabase: DiscographyDatabase): AlbumDao = discographyDatabase.albumDao

    @Provides
    @Singleton
    fun providePlaylistDao(discographyDatabase: DiscographyDatabase): PlaylistDao = discographyDatabase.playlistDao
}