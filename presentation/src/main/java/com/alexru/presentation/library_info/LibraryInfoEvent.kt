package com.alexru.presentation.library_info

/**
 * Events for Library Info screen
 */
sealed class LibraryInfoEvent {
    data class SelectSong(val songId: Int, val selected: Boolean): LibraryInfoEvent()
    data object DeselectAllSongs: LibraryInfoEvent()
    data object OpenSaveToPlaylistDialog: LibraryInfoEvent()
    data object CloseSaveToPlaylistDialog: LibraryInfoEvent()
    data class SaveToPlaylist(val playlistId: Int): LibraryInfoEvent()
}