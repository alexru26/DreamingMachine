package com.alexru.presentation.discover

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.alexru.presentation.components.PullToRefreshLazyVerticalGrid
import com.alexru.presentation.components.bottom_bar.BottomBar
import com.alexru.presentation.components.top_bar.DiscoverTopBar

/**
 * Stateful Discover screen
 */
@Composable
fun DiscoverScreen(
    navController: NavHostController,
    onAlbumClick: (Int) -> Unit,
    viewModel: DiscoverScreenViewModel = hiltViewModel()
) {
    val state = viewModel.state

    Scaffold(
        topBar = {
            DiscoverTopBar()
        },
        bottomBar = {
            BottomBar(
                visible = true,
                navController = navController,
                currentScreen = "discover"
            )
        }
    ) { innerPadding ->
        if(state.error != null) {
            DiscoverErrorScreen(
                error = state.error.asString(),
                modifier = Modifier
                    .padding(innerPadding)
            )
        }
        else if(state.isLoading && !state.isRefreshing) {
            DiscoverLoadingScreen(
                modifier = Modifier
                    .padding(innerPadding)
            )
        }
        else {
            DiscoverMainScreen(
                state = state,
                viewModel = viewModel,
                onAlbumClick = onAlbumClick,
                modifier = Modifier
                    .padding(innerPadding)
            )
        }
    }
}

/**
 * Stateless Discover main screen
 */
@Composable
fun DiscoverMainScreen(
    state: DiscoverState,
    viewModel: DiscoverScreenViewModel,
    onAlbumClick: (Int) -> Unit,
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
//                    DiscoverEvent.OnSearchQueryChange(it)
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
        PullToRefreshLazyVerticalGrid(
            items = state.albums,
            content = { album ->
                DiscoverItem(
                    album = album,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clickable {
                            onAlbumClick(album.id)
                        }
                )
            },
            isRefreshing = state.isRefreshing,
            onRefresh = { viewModel.onEvent(DiscoverEvent.Refresh) }
        )
    }
}

/**
 * Stateless Discover error screen
 */
@Composable
fun DiscoverErrorScreen(
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
 * Stateless Discover loading screen
 */
@Composable
fun DiscoverLoadingScreen(
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