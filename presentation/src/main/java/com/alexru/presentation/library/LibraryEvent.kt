package com.alexru.presentation.library

import com.alexru.domain.model.Playlist

/**
 * Events for Library screen
 */
sealed class LibraryEvent {
    data class CreatePlaylist(val playlist: Playlist): LibraryEvent()
    data class DeletePlaylist(val playlistId: Int): LibraryEvent()
    data class OnSearchQueryChange(val query: String): LibraryEvent()
}