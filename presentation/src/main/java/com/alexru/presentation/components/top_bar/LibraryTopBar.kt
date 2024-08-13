package com.alexru.presentation.components.top_bar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LibraryTopBar() {
    TopAppBar(
        title = { Text("Library") },
        actions = {
            IconButton(
                onClick = {},
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "search"
                )
            }
            IconButton(
                onClick = {},
            ) {
                Icon(
                    imageVector = Icons.Default.FilterList,
                    contentDescription = "filter"
                )
            }
            IconButton(
                onClick = {},
            ) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "options"
                )
            }
        }
    )
}