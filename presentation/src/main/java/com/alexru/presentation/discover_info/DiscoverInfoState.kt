package com.alexru.presentation.discover_info

import com.alexru.domain.model.Album
import com.alexru.domain.model.Playlist
import com.alexru.domain.model.Song

/**
 * State for Discover Info screen
 */
data class DiscoverInfoState(
    val album: Album? = null,
    val songs: List<Song> = emptyList(),
    val playlists: List<Playlist> = emptyList(),
    val selectedSongs: List<Int> = emptyList(),
    val openSaveToPlaylistDialog: Boolean = false,
    val isLoading: Boolean = false,
    val searchQuery: String = "",
    val error: String? = null
)