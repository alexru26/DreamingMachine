package com.alexru.presentation.components.bottom_bar

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.ramcosta.composedestinations.generated.NavGraphs
import com.ramcosta.composedestinations.spec.DestinationSpec
import com.ramcosta.composedestinations.utils.currentDestinationAsState
import com.ramcosta.composedestinations.utils.rememberDestinationsNavigator
import com.ramcosta.composedestinations.utils.startDestination

/**
 * Main Bottom Navigation Bar
 */
@Composable
fun BottomBar(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val currentDestination: DestinationSpec = navController.currentDestinationAsState().value
        ?: NavGraphs.root.startDestination

    val navigator = navController.rememberDestinationsNavigator()

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
        modifier = modifier
    ) {
        BottomBarDestination.entries.forEach { destination ->
            val isCurrentDestination = destination.navGraph.destinations.contains(currentDestination)
            NavigationBarItem(
                selected = isCurrentDestination,
                onClick = {
                    if (isCurrentDestination) {
                        // When we click again on a bottom bar item and it was already selected
                        // we want to pop the back stack until the initial destination of this bottom bar item
                        navigator.popBackStack(destination.direction, false)
                        return@NavigationBarItem
                    }

                    navigator.navigate(destination.direction) {
                        // Pop up to the root of the graph to
                        // avoid building up a large stack of destinations
                        // on the back stack as users select items
                        popUpTo(NavGraphs.root) {
                            saveState = true
                        }

                        // Avoid multiple copies of the same destination when
                        // re-selecting the same item
                        launchSingleTop = true
                        // Restore state when re-selecting a previously selected item
                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        imageVector = destination.icon,
                        contentDescription = stringResource(id = destination.label)
                    )
                },
                label = { Text(text = stringResource(id = destination.label)) }
            )
        }
    }
}