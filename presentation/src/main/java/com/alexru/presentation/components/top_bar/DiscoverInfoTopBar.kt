package com.alexru.presentation.components.top_bar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiscoverInfoTopBar(
    onBackClick: () -> Unit
) {
    TopAppBar(
        title = { },
        actions = {
//            IconButton(
//                onClick = {},
//            ) {
//                Icon(
//                    imageVector = Icons.Default.Download,
//                    contentDescription = "download"
//                )
//            }
//            IconButton(
//                onClick = {},
//            ) {
//                Icon(
//                    imageVector = Icons.Default.FilterList,
//                    contentDescription = "filter"
//                )
//            }
            IconButton(
                onClick = {},
            ) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "options"
                )
            }
        },
        navigationIcon = {
            IconButton(
                onClick = onBackClick
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "back"
                )
            }
        }
    )
}