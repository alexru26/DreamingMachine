package com.alexru.presentation.discover_info

/**
 * Events for Discover Info screen
 */
sealed class DiscoverInfoEvent {
    data class SaveToPlaylist(val playlistId: Int, val songId: Int): DiscoverInfoEvent()
}