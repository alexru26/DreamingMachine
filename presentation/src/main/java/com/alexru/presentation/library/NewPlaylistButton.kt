package com.alexru.presentation.library

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.alexru.domain.model.Playlist

/**
 * Button to add new playlist
 */
@Composable
fun NewPlaylistButton(
    onCreatePlaylist: (Playlist) -> Unit,
    modifier: Modifier = Modifier
) {
    var dialogExpanded by remember { mutableStateOf(false) }
    var textFieldValue by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }

    FloatingActionButton(
        onClick = {
            dialogExpanded = true
        },
        shape = RoundedCornerShape(96.dp),
        modifier = modifier
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(
                    start = 18.dp,
                    end = 24.dp
                )
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "add"
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "New",
                fontSize = 16.sp,
                style = MaterialTheme.typography.labelLarge
            )
        }
    }

    if(dialogExpanded) {
        Dialog(
            onDismissRequest = { dialogExpanded = false },
            properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true)
        ) {
            Surface(
                shape = MaterialTheme.shapes.medium,
                color = MaterialTheme.colorScheme.surface
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                ) {
                    Text(
                        text = "New playlist",
                        fontSize = 18.sp,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = textFieldValue,
                        onValueChange = {
                            textFieldValue = it
                            isError = it.isEmpty()
                        },
                        label = { Text("Required") },
                        isError = isError,
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    if (isError) {
                        Text(
                            text = "This field cannot be empty",
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        horizontalArrangement = Arrangement.End,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        TextButton(onClick = { dialogExpanded = false }) {
                            Text("Cancel")
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        Button(
                            onClick = {
                                if (textFieldValue.isNotEmpty()) {
                                    dialogExpanded = false
                                    val playlist = Playlist(
                                        name = textFieldValue,
                                        songs = emptySet()
                                    )
                                    onCreatePlaylist(playlist)
                                    textFieldValue = ""
                                } else {
                                    isError = true
                                }
                            }
                        ) {
                            Text("Create")
                        }
                    }
                }
            }
        }
    }
}