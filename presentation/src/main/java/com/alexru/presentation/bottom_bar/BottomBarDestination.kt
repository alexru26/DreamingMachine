package com.alexru.presentation.bottom_bar

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
import com.ramcosta.composedestinations.spec.DirectionDestinationSpec
import com.ramcosta.composedestinations.spec.DirectionNavGraphSpec

/**
 * Destinations for [BottomBar]
 */
enum class BottomBarDestination(
    val direction: DirectionDestinationSpec,
    val icon: ImageVector,
    @StringRes val label: Int
) {
    Library(LibraryScreenDestination, Icons.Default.LibraryMusic, R.string.library_screen),
    Discover(DiscoverScreenDestination, Icons.Default.Explore, R.string.discover_screen),
    Settings(SettingsScreenDestination, Icons.Default.Settings, R.string.settings_screen)
}