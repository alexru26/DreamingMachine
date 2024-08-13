package com.alexru.presentation.discover

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.alexru.presentation.components.DiscoverGraph
import com.alexru.presentation.components.PullToRefreshLazyVerticalGrid
import com.alexru.presentation.components.top_bar.DiscoverTopBar
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.generated.destinations.DiscoverInfoScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

/**
 * Stateful Discover screen
 */
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
@Destination<DiscoverGraph>(start = true)
fun DiscoverScreen(
    navigator: DestinationsNavigator,
    viewModel: DiscoverScreenViewModel = hiltViewModel()
) {
    val state = viewModel.state

    Scaffold(
        topBar = {
            DiscoverTopBar()
        }
    ) {
        // TODO: I HATE NAVIGATION
        if(state.error != null) {
            DiscoverErrorScreen(
                error = state.error
            )
        }
        else if(state.isLoading && !state.isRefreshing) {
            DiscoverLoadingScreen(

            )
        }
        else {
            DiscoverMainScreen(
                navigator = navigator,
                state = state,
                viewModel = viewModel
            )
        }
    }
}

/**
 * Stateless Discover main screen
 */
@Composable
fun DiscoverMainScreen(
    navigator: DestinationsNavigator,
    state: DiscoverState,
    viewModel: DiscoverScreenViewModel,
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
                            navigator.navigate(
                                DiscoverInfoScreenDestination(album.id)
                            ) {
                                launchSingleTop = true
                                restoreState = true
                            }
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