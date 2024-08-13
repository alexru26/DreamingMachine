package com.alexru.presentation.components.bottom_bar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.LibraryMusic
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.alexru.presentation.R
import com.alexru.presentation.navigation.Discover
import com.alexru.presentation.navigation.DiscoverScreenDestination
import com.alexru.presentation.navigation.Library
import com.alexru.presentation.navigation.LibraryScreenDestination

/**
 * Main Bottom Navigation Bar
 */
@Composable
fun BottomBar(
    visible: Boolean,
    navController: NavHostController,
    currentScreen: String,
    modifier: Modifier = Modifier
) {
    AnimatedVisibility(
        visible = visible,
        enter = expandVertically(animationSpec = tween(delayMillis = 300)),
        exit = shrinkVertically(animationSpec = tween()),
    ) {
        NavigationBar(
            containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
            modifier = modifier
        ) {
            NavigationBarItem(
                selected = currentScreen == "library",
                onClick = {
                    if (currentScreen == "library") {
                        // When we click again on a bottom bar item and it was already selected
                        // we want to pop the back stack until the initial destination of this bottom bar item
                        navController.popBackStack(LibraryScreenDestination, inclusive = false)
                        return@NavigationBarItem
                    }

                    navController.navigate(Library) {
                        // Pop up to the root of the graph to
                        // avoid building up a large stack of destinations
                        // on the back stack as users select items
//                    popUpTo(Library) {
//                        saveState = true
//                    }

                        // Avoid multiple copies of the same destination when
                        // re-selecting the same item
                        launchSingleTop = true
                        // Restore state when re-selecting a previously selected item
                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        imageVector = Icons.Default.LibraryMusic,
                        contentDescription = "library"
                    )
                },
                label = { Text(text = stringResource(R.string.library_screen)) }
            )
            NavigationBarItem(
                selected = currentScreen == "discover",
                onClick = {
                    if (currentScreen == "discover") {
                        // When we click again on a bottom bar item and it was already selected
                        // we want to pop the back stack until the initial destination of this bottom bar item
                        navController.popBackStack(DiscoverScreenDestination, inclusive = false)
                        return@NavigationBarItem
                    }

                    navController.navigate(Discover) {
                        // Pop up to the root of the graph to
                        // avoid building up a large stack of destinations
                        // on the back stack as users select items
//                    popUpTo(DiscoverScreenDestination) {
//                        saveState = true
//                    }

                        // Avoid multiple copies of the same destination when
                        // re-selecting the same item
                        launchSingleTop = true
                        // Restore state when re-selecting a previously selected item
                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        imageVector = Icons.Default.Explore,
                        contentDescription = "discover"
                    )
                },
                label = { Text(text = stringResource(R.string.discover_screen)) }
            )
        }
    }
}