package com.alexru.presentation.library

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.alexru.presentation.navgraphs.LibraryGraph
import com.alexru.presentation.playlist_item.PlaylistItem
import com.alexru.presentation.playlist_item.PlaylistItemMenuOption
import com.alexru.presentation.playlist_item.PlaylistItemOptionsBottomSheet
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.generated.destinations.LibraryInfoScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

/**
 * Stateful Library screen
 */
@Composable
@Destination<LibraryGraph>(start = true)
fun LibraryScreen(
    navigator: DestinationsNavigator,
    viewModel: LibraryScreenViewModel = hiltViewModel()
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val state = viewModel.state
        if(state.error != null) {
            LibraryErrorScreen(state.error)
        }
        else if(state.isLoading) {
            LibraryLoadingScreen()
        }
        else {
            LibraryMainScreen(
                navigator = navigator,
                state = state,
                viewModel = viewModel
            )
        }
        
        NewPlaylistButton(
            viewModel = viewModel,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        )
    }
}

/**
 * Stateless Library main screen
 */
@Composable
fun LibraryMainScreen(
    navigator: DestinationsNavigator,
    state: LibraryState,
    viewModel: LibraryScreenViewModel,
    modifier: Modifier = Modifier
) {
    var openOptionsBottomSheet by rememberSaveable { mutableStateOf(false) }

    var selectedPlaylistId by rememberSaveable { mutableIntStateOf(0) }

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = state.searchQuery,
            onValueChange = {
                viewModel.onEvent(
                    LibraryEvent.OnSearchQueryChange(it)
                )
            },
            placeholder = {
                Text(text = "Search...")
            },
            maxLines = 1,
            singleLine = true,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {
            items(state.playlists.size) { i ->
                val playlist = state.playlists[i]
                PlaylistItem(
                    playlist = playlist,
                    onOptionsClicked = {
                        selectedPlaylistId = playlist.id
                        openOptionsBottomSheet = true
                    },
                    modifier = Modifier
                        .clickable {
                            navigator.navigate(
                                LibraryInfoScreenDestination(playlist.id)
                            ) {
                                launchSingleTop = true
                                restoreState = true
                            }
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

    if(openOptionsBottomSheet) {
        PlaylistItemOptionsBottomSheet(
            onDismissRequest = {  },
            onOptionClicked = { option ->
                openOptionsBottomSheet = false
                when(option) {
                    PlaylistItemMenuOption.DeletePlaylist -> {
                        viewModel.onEvent(
                            LibraryEvent.DeletePlaylist(selectedPlaylistId)
                        )
                    }
                }
            }
        )
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