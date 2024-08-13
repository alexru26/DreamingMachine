package com.alexru.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.toRoute
import com.alexru.presentation.discover.DiscoverScreen
import com.alexru.presentation.discover_info.DiscoverInfoScreen
import kotlinx.serialization.Serializable

@Serializable
object Discover

@Serializable
object DiscoverScreenDestination

@Serializable
data class DiscoverInfoScreenDestination(
    val albumId: Int
)

fun NavGraphBuilder.discoverGraph(
    navController: NavHostController
) {
    navigation<Discover>(startDestination = DiscoverScreenDestination) {
        composable<DiscoverScreenDestination> {
            DiscoverScreen(
                navController = navController,
                onAlbumClick = { albumId ->
                    navController.navigate(
                        DiscoverInfoScreenDestination(
                            albumId = albumId
                        )
                    ) {
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
        composable<DiscoverInfoScreenDestination> {
            val args = it.toRoute<DiscoverInfoScreenDestination>()
            DiscoverInfoScreen(
                navController = navController,
                albumId = args.albumId
            )
        }
    }
}