package com.alexru.presentation.discover

import com.alexru.domain.model.Album
import com.alexru.presentation.util.UiText

/**
 * State for Discover screen
 */
data class DiscoverState(
    val albums: List<Album> = emptyList(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val searchQuery: String = "",
    val error: UiText? = null
)