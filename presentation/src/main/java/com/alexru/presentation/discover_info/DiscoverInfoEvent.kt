package com.alexru.presentation.discover_info

/**
 * Events for Discover Info screen
 */
sealed class DiscoverInfoEvent {
    data class SelectSong(val songId: Int, val selected: Boolean): DiscoverInfoEvent()
    data object DeselectAllSongs: DiscoverInfoEvent()
    data object OpenSaveToPlaylistDialog: DiscoverInfoEvent()
    data object CloseSaveToPlaylistDialog: DiscoverInfoEvent()
    data class SaveToPlaylist(val playlistId: Int): DiscoverInfoEvent()
}