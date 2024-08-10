package com.alexru.presentation.discover_info

import androidx.compose.foundation.clickable
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.alexru.presentation.navgraphs.DiscoverGraph
import com.alexru.presentation.song_item.SaveToPlaylistDialog
import com.alexru.presentation.song_item.SongItem
import com.alexru.presentation.song_item.SongItemMenuOption
import com.alexru.presentation.song_item.SongItemOptionsBottomSheet
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

/**
 * Stateful Discover Info screen
 */
@Composable
@Destination<DiscoverGraph>
fun DiscoverInfoScreen(
    navigator: DestinationsNavigator,
    albumId: Int,
    viewModel: DiscoverInfoScreenViewModel = hiltViewModel()
) {
    val state = viewModel.state
    if(state.error != null) {
        DiscoverInfoErrorScreen(state.error)
    }
    else if(state.isLoading) {
        DiscoverInfoLoadingScreen()
    }
    else {
        DiscoverInfoMainScreen(
            navigator = navigator,
            state = state,
            viewModel = viewModel
        )
    }
}

/**
 * Stateless Discover Info main screen
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiscoverInfoMainScreen(
    navigator: DestinationsNavigator,
    state: DiscoverInfoState,
    viewModel: DiscoverInfoScreenViewModel,
    modifier: Modifier = Modifier
) {
    var openSavePlaylistBottomSheet by remember { mutableStateOf(false) }
    var openOptionsBottomSheet by rememberSaveable { mutableStateOf(false) }

    var selectedSongId by rememberSaveable { mutableIntStateOf(0) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
    ) {
        state.album?.let {
            Spacer(modifier = Modifier.height(48.dp))
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
                    SongItem(
                        song = song,
                        onOptionsClicked = {
                            selectedSongId = song.songId
                            openOptionsBottomSheet = true
                        },
                        modifier = Modifier
                            .clickable {

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

    // TODO: Everything about this is scuffed ngl
    if(openOptionsBottomSheet) {
        SongItemOptionsBottomSheet(
            onDismissRequest = { openOptionsBottomSheet = false },
            onOptionClicked = { option ->
                openOptionsBottomSheet = false
                when(option) {
                    SongItemMenuOption.SaveToPlaylist -> {
                        openSavePlaylistBottomSheet = true
                    }
                }
            }
        )
    }

    if(openSavePlaylistBottomSheet) {
        SaveToPlaylistDialog(
            playlists = state.playlists,
            onDismissRequest = { openSavePlaylistBottomSheet = false },
            saveToPlaylist = { playlistId ->
                viewModel.onEvent(
                    DiscoverInfoEvent.SaveToPlaylist(playlistId, selectedSongId)
                )
                openSavePlaylistBottomSheet = false
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