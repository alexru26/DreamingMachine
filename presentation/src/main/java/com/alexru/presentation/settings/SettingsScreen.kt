package com.alexru.presentation.settings

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

/**
 * Settings screen
 */
@Composable
@Destination<RootGraph>
fun SettingsScreen(
    navigator: DestinationsNavigator
) {
    Text(
        text = "Settings lmao",
    )
}