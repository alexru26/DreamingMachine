package com.alexru.presentation.library

import com.alexru.domain.model.Playlist
import com.alexru.presentation.util.UiText

/**
 * State for Library screen
 */
data class LibraryState(
    val playlists: List<Playlist> = emptyList(),
    val selectedPlaylists: List<Int> = emptyList(),
    val isLoading: Boolean = false,
    val searchQuery: String = "",
    val error: UiText? = null
)