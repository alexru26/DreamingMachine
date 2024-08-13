package com.alexru.presentation.library_info

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.alexru.presentation.components.bottom_bar.SongOptionsBottomMenu
import com.alexru.presentation.components.SaveToPlaylistDialog
import com.alexru.presentation.components.SongItem
import com.alexru.presentation.components.bottom_bar.BottomBar
import com.alexru.presentation.util.selectedBackground

/**
 * Stateful Library Info screen
 */
@Composable
fun LibraryInfoScreen(
    navController: NavHostController,
    playlistId: Int,
    viewModel: LibraryInfoScreenViewModel = hiltViewModel()
) {
    val state = viewModel.state

    Scaffold(
        bottomBar = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.BottomEnd,
            ) {
                BottomBar(
                    visible = state.selectedSongs.isEmpty(),
                    navController = navController,
                    currentScreen = "library"
                )
                SongOptionsBottomMenu(
                    visible = state.selectedSongs.isNotEmpty(),
                    onSaveToPlaylist = { viewModel.onEvent(LibraryInfoEvent.OpenSaveToPlaylistDialog) },
                    onDownload = {  }
                )
            }
        }
    ) { innerPaddings ->
        if(state.error != null) {
            LibraryInfoErrorScreen(
                error = state.error,
                modifier = Modifier
                    .padding(innerPaddings)
            )
        }
        else if(state.isLoading) {
            LibraryInfoLoadingScreen(
                modifier = Modifier
                    .padding(innerPaddings)
            )
        }
        else {
            LibraryInfoMainScreen(
                state = state,
                viewModel = viewModel,
                modifier = Modifier
                    .padding(innerPaddings)
            )
        }
    }
}

/**
 * Stateless Library Info main screen
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LibraryInfoMainScreen(
    state: LibraryInfoState,
    viewModel: LibraryInfoScreenViewModel,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
    ) {
        state.playlist?.let {
            Spacer(modifier = Modifier.height(48.dp))
            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
            ) {
                item {
                    Box(
                        modifier = Modifier
                            .size(150.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(Color.Gray)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = state.playlist.name,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.titleLarge
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
                items(state.songs) { song ->
                    val haptic = LocalHapticFeedback.current
                    val selected = state.selectedSongs.contains(song.songId)
                    SongItem(
                        song = song,
                        onDownload = { },
                        modifier = Modifier
                            .combinedClickable(
                                onClick = {},
                                onLongClick = {
                                    viewModel.onEvent(
                                        LibraryInfoEvent.SelectSong(song.songId, selected)
                                    )
                                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                }
                            )
                            .selectedBackground(selected)
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

    if(state.openSaveToPlaylistDialog) {
        SaveToPlaylistDialog(
            playlists = state.playlists,
            onDismissRequest = {
                viewModel.onEvent(
                    LibraryInfoEvent.CloseSaveToPlaylistDialog
                )
            },
            saveToPlaylist = { playlistId ->
                viewModel.onEvent(
                    LibraryInfoEvent.SaveToPlaylist(playlistId),
                    LibraryInfoEvent.CloseSaveToPlaylistDialog,
                    LibraryInfoEvent.DeselectAllSongs
                )
            },
            modifier = Modifier
                .fillMaxSize()
        )
    }
}

/**
 * Stateless Library Info error screen
 */
@Composable
fun LibraryInfoErrorScreen(
    error: String,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxSize()
    ) {
        Text(error)
    }
}

/**
 * Stateless Library Info loading screen
 */
@Composable
fun LibraryInfoLoadingScreen(
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxSize()
    ) {
        CircularProgressIndicator()
    }
}