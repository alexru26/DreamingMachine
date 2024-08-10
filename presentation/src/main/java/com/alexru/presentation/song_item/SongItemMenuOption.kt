package com.alexru.presentation.song_item

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LibraryAdd
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Menu options for SongItem
 */
enum class SongItemMenuOption(
    val text: String,
    val icon: ImageVector
) {
    SaveToPlaylist("Save to playlist", Icons.Default.LibraryAdd)
}
