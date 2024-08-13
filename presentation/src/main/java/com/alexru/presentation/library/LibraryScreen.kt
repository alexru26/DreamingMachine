package com.alexru.presentation.library

import android.annotation.SuppressLint
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.alexru.presentation.components.bottom_bar.PlaylistOptionsBottomMenu
import com.alexru.presentation.components.PlaylistItem
import com.alexru.presentation.components.bottom_bar.BottomBar
import com.alexru.presentation.components.top_bar.LibraryTopBar
import com.alexru.presentation.util.selectedBackground

/**
 * Stateful Library screen
 */
@Composable
fun LibraryScreen(
    navController: NavHostController,
    onPlaylistClick: (Int) -> Unit,
    viewModel: LibraryScreenViewModel = hiltViewModel()
) {
    val state = viewModel.state

    Scaffold(
        topBar = {
            LibraryTopBar()
        },
        floatingActionButton = {
            NewPlaylistButton(
                onCreatePlaylist = { viewModel.onEvent(LibraryEvent.CreatePlaylist(it)) }
            )
        },
        bottomBar = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.BottomEnd
            ) {
                BottomBar(
                    visible = state.selectedPlaylists.isEmpty(),
                    navController = navController,
                    currentScreen = "library"
                )
                PlaylistOptionsBottomMenu(
                    visible = state.selectedPlaylists.isNotEmpty(),
                    onDeletePlaylist = { viewModel.onEvent(LibraryEvent.DeletePlaylist) },
                    onDownload = { }
                )
            }
        }
    ) { innerPadding ->
        if(state.error != null) {
            LibraryErrorScreen(
                error = state.error,
                modifier = Modifier
                    .padding(innerPadding)
            )
        }
        else if(state.isLoading) {
            LibraryLoadingScreen(
                modifier = Modifier
                    .padding(innerPadding)
            )
        }
        else {
            LibraryMainScreen(
                state = state,
                viewModel = viewModel,
                onPlaylistClick = onPlaylistClick,
                modifier = Modifier
                    .padding(innerPadding)
            )
        }
    }
}

/**
 * Stateless Library main screen
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LibraryMainScreen(
    state: LibraryState,
    viewModel: LibraryScreenViewModel,
    onPlaylistClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
//        Spacer(modifier = Modifier.height(16.dp))
//        OutlinedTextField(
//            value = state.searchQuery,
//            onValueChange = {
//                viewModel.onEvent(
//                    LibraryEvent.OnSearchQueryChange(it)
//                )
//            },
//            placeholder = {
//                Text(text = "Search...")
//            },
//            maxLines = 1,
//            singleLine = true,
//            modifier = Modifier
//                .padding(16.dp)
//                .fillMaxWidth()
//        )
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {
            items(state.playlists) { playlist ->
                val haptic = LocalHapticFeedback.current
                val selected = state.selectedPlaylists.contains(playlist.id)
                PlaylistItem(
                    playlist = playlist,
                    showDownload = true,
                    onDownload = {  },
                    modifier = Modifier
                        .combinedClickable(
                            onClick = {
                                onPlaylistClick(playlist.id)
                            },
                            onLongClick = {
                                viewModel.onEvent(
                                    LibraryEvent.SelectPlaylist(playlist.id, selected)
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

/**
 * Stateless library error screen
 */
@Composable
fun LibraryErrorScreen(
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
 * Stateless library loading screen
 */
@Composable
fun LibraryLoadingScreen(
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