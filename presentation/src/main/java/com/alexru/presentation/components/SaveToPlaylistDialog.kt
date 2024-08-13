package com.alexru.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.alexru.domain.model.Playlist

/**
 * Dialog to display Playlists to save a Song
 */
@Composable
fun SaveToPlaylistDialog(
    playlists: List<Playlist>,
    onDismissRequest: () -> Unit,
    saveToPlaylist: (playlistId: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Dialog(
        onDismissRequest = onDismissRequest,
    ) {
        Surface(
            color = MaterialTheme.colorScheme.surface,
            modifier = modifier
                .wrapContentHeight()
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
            ) {
                // Title with close button
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Save song to playlist",
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = Modifier.weight(1f)
                    )
                    IconButton(onClick = onDismissRequest) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // List of playlists
                LazyColumn {
                    items(playlists) { playlist ->
                        PlaylistItem(
                            playlist = playlist,
                            modifier = Modifier
                                .clickable {
                                    saveToPlaylist(playlist.id)
                                }
                                .padding(
                                    vertical = 2.dp,
                                    horizontal = 16.dp
                                )
                                .fillMaxWidth()
                        )
                    }
                }
            }
        }
    }
}