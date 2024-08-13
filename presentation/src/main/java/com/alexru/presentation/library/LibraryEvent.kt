package com.alexru.presentation.library

import com.alexru.domain.model.Playlist

/**
 * Events for Library screen
 */
sealed class LibraryEvent {
    data class SelectPlaylist(val playlistId: Int, val selected: Boolean): LibraryEvent()
    data object DeselectAllSongs: LibraryEvent()
    data class CreatePlaylist(val playlist: Playlist): LibraryEvent()
    data object DeletePlaylist: LibraryEvent()
    data class OnSearchQueryChange(val query: String): LibraryEvent()
}