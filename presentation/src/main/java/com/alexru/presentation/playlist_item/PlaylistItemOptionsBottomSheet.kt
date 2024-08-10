package com.alexru.presentation.playlist_item

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp

/**
 * Bottom Sheet to display PlaylistItem options
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaylistItemOptionsBottomSheet(
    onDismissRequest: () -> Unit,
    onOptionClicked: (PlaylistItemMenuOption) -> Unit,
    modifier: Modifier = Modifier
) {
    val sheetState = rememberModalBottomSheetState()
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        dragHandle = {},
        shape = RectangleShape,
        windowInsets = WindowInsets(0), // TODO: temp fix
        modifier = modifier
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(bottom = 48.dp) // TODO: temp fix
        ) {
            items(PlaylistItemMenuOption.entries) { option ->
                ListItem(
                    headlineContent = { Text(text = option.text) },
                    leadingContent = {
                        Icon(
                            imageVector = option.icon,
                            contentDescription = "option"
                        )
                    },
                    modifier = Modifier
                        .clickable {
                            onOptionClicked(option)
                        }
                )
            }
        }
    }
}