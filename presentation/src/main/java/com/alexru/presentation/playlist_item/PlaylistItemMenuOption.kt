package com.alexru.presentation.playlist_item

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Menu options for PlaylistItem
 */
enum class PlaylistItemMenuOption(
    val text: String,
    val icon: ImageVector
) {
    DeletePlaylist("Delete playlist", Icons.Default.Delete)
}
