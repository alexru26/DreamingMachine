package com.alexru.data_remote.networking

import com.alexru.data_remote.dto.AlbumDto
import com.alexru.data_remote.dto.SongDto
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Retrofit API
 */
interface DiscographyApi {

    @GET("albums")
    suspend fun getAlbums(): List<AlbumDto>

    @GET("albums/{albumId}")
    suspend fun getAlbum(
        @Path("albumId") albumId: Int
    ): AlbumDto

    @GET("albums/{albumId}/songs")
    suspend fun getSongs(
        @Path("albumId") albumId: Int
    ): List<SongDto>

    @GET("songs/{songId}")
    suspend fun getSong(
        @Path("songId") songId: Int
    ): SongDto

    @GET("songs/{songId}/download")
    suspend fun downloadSong(
        @Path("songId") songId: Int
    ): ResponseBody

    companion object {
        const val BASE_URL = "https://susumuhirasawa-discography.vercel.app/api/"
    }
}