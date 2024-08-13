package com.alexru.presentation.components.bottom_bar

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.LibraryMusic
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.alexru.presentation.R
import com.ramcosta.composedestinations.generated.destinations.DiscoverScreenDestination
import com.ramcosta.composedestinations.generated.destinations.LibraryScreenDestination
import com.ramcosta.composedestinations.generated.destinations.SettingsScreenDestination
import com.ramcosta.composedestinations.generated.navgraphs.DiscoverNavGraph
import com.ramcosta.composedestinations.generated.navgraphs.LibraryNavGraph
import com.ramcosta.composedestinations.generated.navgraphs.RootNavGraph
import com.ramcosta.composedestinations.spec.DirectionDestinationSpec
import com.ramcosta.composedestinations.spec.NavGraphSpec

/**
 * Destinations for [BottomBar]
 */
enum class BottomBarDestination(
    val direction: DirectionDestinationSpec,
    val navGraph: NavGraphSpec,
    val icon: ImageVector,
    @StringRes val label: Int
) {
    Library(
        direction = LibraryScreenDestination,
        navGraph = LibraryNavGraph,
        icon = Icons.Default.LibraryMusic,
        label = R.string.library_screen
    ),
    Discover(
        direction = DiscoverScreenDestination,
        navGraph = DiscoverNavGraph,
        icon = Icons.Default.Explore,
        label = R.string.discover_screen
    ),
    Settings(
        direction = SettingsScreenDestination,
        navGraph = RootNavGraph,
        icon = Icons.Default.Settings,
        label = R.string.settings_screen
    )
}