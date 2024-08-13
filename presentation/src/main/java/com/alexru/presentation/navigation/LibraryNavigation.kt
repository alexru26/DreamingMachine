package com.alexru.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.toRoute
import com.alexru.presentation.library.LibraryScreen
import com.alexru.presentation.library_info.LibraryInfoScreen
import kotlinx.serialization.Serializable

@Serializable
object Library

@Serializable
object LibraryScreenDestination

@Serializable
data class LibraryInfoScreenDestination(
    val playlistId: Int
)

fun NavGraphBuilder.libraryGraph(
    navController: NavHostController
) {
    navigation<Library>(startDestination = LibraryScreenDestination) {
        composable<LibraryScreenDestination> {
            LibraryScreen(
                navController = navController,
                onPlaylistClick = { playlistId ->
                    navController.navigate(
                        LibraryInfoScreenDestination(
                            playlistId = playlistId
                        )
                    ) {
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
        composable<LibraryInfoScreenDestination> {
            val args = it.toRoute<LibraryInfoScreenDestination>()
            LibraryInfoScreen(
                navController = navController,
                playlistId = args.playlistId
            )
        }
    }
}