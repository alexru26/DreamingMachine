package com.alexru.presentation.library_info

/**
 * Events for Library Info screen
 */
sealed class LibraryInfoEvent {
    data class SaveToPlaylist(val playlistId: Int, val songId: Int): LibraryInfoEvent()
}