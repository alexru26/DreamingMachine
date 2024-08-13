package com.alexru.presentation.components.bottom_bar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Download
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Bottom Menu Bar for Playlist options
 */
@Composable
fun PlaylistOptionsBottomMenu(
    visible: Boolean,
    onDeletePlaylist: () -> Unit,
    onDownload: () -> Unit,
    modifier: Modifier = Modifier
) {
    AnimatedVisibility(
        visible = visible,
        enter = expandVertically(animationSpec = tween(delayMillis = 300)),
        exit = shrinkVertically(animationSpec = tween()),
    ) {
        Surface(
            shape = MaterialTheme.shapes.large.copy(bottomEnd = ZeroCornerSize, bottomStart = ZeroCornerSize),
            color = MaterialTheme.colorScheme.surfaceContainerHigh,
            modifier = modifier
                .fillMaxWidth()
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                modifier = Modifier
                    .padding(horizontal = 8.dp, vertical = 12.dp),
            ) {
                IconButton(
                    onClick = onDeletePlaylist
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "delete"
                    )
                }
                IconButton(
                    onClick = onDownload
                ) {
                    Icon(
                        imageVector = Icons.Default.Download,
                        contentDescription = "download"
                    )
                }
            }
        }
    }
}