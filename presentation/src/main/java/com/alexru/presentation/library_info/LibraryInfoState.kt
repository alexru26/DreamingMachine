package com.alexru.presentation.library_info

import com.alexru.domain.model.Playlist
import com.alexru.domain.model.Song

/**
 * State for Library Info screen
 */
data class LibraryInfoState(
    val playlist: Playlist? = null,
    val songs: List<Song> = emptyList(),
    val playlists: List<Playlist> = emptyList(),
    val selectedSongs: List<Int> = emptyList(),
    val openSaveToPlaylistDialog: Boolean = false,
    val isLoading: Boolean = false,
    val searchQuery: String = "",
    val error: String? = null
)