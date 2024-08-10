package com.alexru.presentation.discover

/**
 * Events for Discover screen
 */
sealed class DiscoverEvent {
    data object Refresh: DiscoverEvent()
    data class OnSearchQueryChange(val query: String): DiscoverEvent()
}