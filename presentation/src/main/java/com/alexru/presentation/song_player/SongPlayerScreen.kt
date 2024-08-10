package com.alexru.presentation.song_player

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

/**
 * SongPlayerScreen
 */
@Composable
fun SongPlayerScreen(
    navigator: DestinationsNavigator
) {
    Text("song player")
}