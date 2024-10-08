package com.alexru.presentation.discover_info

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.alexru.presentation.components.bottom_bar.SongOptionsBottomMenu
import com.alexru.presentation.components.SaveToPlaylistDialog
import com.alexru.presentation.components.SongItem
import com.alexru.presentation.components.bottom_bar.BottomBar
import com.alexru.presentation.components.top_bar.DiscoverInfoTopBar
import com.alexru.presentation.util.selectedBackground

/**
 * Stateful Discover Info screen
 */
@Composable
fun DiscoverInfoScreen(
    navController: NavHostController,
    albumId: Int,
    viewModel: DiscoverInfoScreenViewModel = hiltViewModel()
) {
    val state = viewModel.state

    Scaffold(
        topBar = {
            DiscoverInfoTopBar(
                onBackClick = { navController.navigateUp() }
            )
        },
        bottomBar = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.BottomEnd,
            ) {
                BottomBar(
                    visible = state.selectedSongs.isEmpty(),
                    navController = navController,
                    currentScreen = "discover"
                )
                SongOptionsBottomMenu(
                    visible = state.selectedSongs.isNotEmpty(),
                    onSaveToPlaylist = { viewModel.onEvent(DiscoverInfoEvent.OpenSaveToPlaylistDialog) },
                    onDownload = {  }
                )
            }
        }
    ) { innerPadding ->
        if(state.error != null) {
            DiscoverInfoErrorScreen(
                error = state.error.asString(),
                modifier = Modifier
                    .padding(innerPadding)
            )
        }
        else if(state.isLoading) {
            DiscoverInfoLoadingScreen(
                modifier = Modifier
                    .padding(innerPadding)
            )
        }
        else {
            DiscoverInfoMainScreen(
                state = state,
                viewModel = viewModel,
                modifier = Modifier
                    .padding(innerPadding)
            )
        }
    }
}

/**
 * Stateless Discover Info main screen
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DiscoverInfoMainScreen(
    state: DiscoverInfoState,
    viewModel: DiscoverInfoScreenViewModel,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
    ) {
        state.album?.let {
            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
            ) {
                item {
                    AsyncImage(
                        model = state.album.cover,
                        contentDescription = state.album.name,
                        modifier = Modifier
                            .height(150.dp)
                            .clip(RoundedCornerShape(4.dp))
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = state.album.name,
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
                                        DiscoverInfoEvent.SelectSong(song.songId, selected)
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
                    DiscoverInfoEvent.CloseSaveToPlaylistDialog
                )
            },
            saveToPlaylist = { playlistId ->
                viewModel.onEvent(
                    DiscoverInfoEvent.SaveToPlaylist(playlistId),
                    DiscoverInfoEvent.CloseSaveToPlaylistDialog
                )
            },
            modifier = Modifier
                .fillMaxSize()
        )
    }
}

/**
 * Stateless Discover Info error screen
 */
@Composable
fun DiscoverInfoErrorScreen(
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
 * Stateless Discover Info loading screen
 */
@Composable
fun DiscoverInfoLoadingScreen(
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