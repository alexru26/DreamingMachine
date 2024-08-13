package com.alexru.presentation.library_info

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexru.domain.repository.DiscographyRepository
import com.alexru.domain.resource.Result
import com.alexru.presentation.util.asErrorUiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for Library Info screen
 */
@HiltViewModel
class LibraryInfoScreenViewModel @Inject constructor(
    private val repository: DiscographyRepository,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {

    var state by mutableStateOf(LibraryInfoState())

    init {
        getSongs()
    }

    fun onEvent(vararg events: LibraryInfoEvent) {
        events.forEach { event ->
            when(event) {
                is LibraryInfoEvent.SelectSong -> {
                    state = if(event.selected) {
                        state.copy(
                            selectedSongs = state.selectedSongs-event.songId
                        )
                    } else {
                        state.copy(
                            selectedSongs = state.selectedSongs+event.songId
                        )
                    }
                }
                is LibraryInfoEvent.DeselectAllSongs -> {
                    state = state.copy(
                        selectedSongs = emptyList()
                    )
                }
                is LibraryInfoEvent.OpenSaveToPlaylistDialog -> {
                    getPlaylists()
                    state = state.copy(
                        openSaveToPlaylistDialog = true
                    )
                }
                is LibraryInfoEvent.CloseSaveToPlaylistDialog -> {
                    state = state.copy(
                        openSaveToPlaylistDialog = false
                    )
                }
                is LibraryInfoEvent.SaveToPlaylist -> updatePlaylistSongs(event.playlistId)
            }
        }
    }

    private fun getSongs() {
        viewModelScope.launch {
            val playlistId = savedStateHandle.get<Int>("playlistId") ?: return@launch

            state = state.copy(
                playlist = repository.getPlaylist(playlistId)
            )
            repository.getSongs(state.playlist!!.songs)
                .collect { result ->
                    when(result) {
                        is Result.Success -> {
                            state = state.copy(
                                songs = result.data,
                            )
                        }
                        is Result.Error -> {
                            state = state.copy(
                                error = result.asErrorUiText()
                            )
                        }
                        is Result.Loading -> {
                            state = state.copy(
                                isLoading = result.isLoading
                            )
                        }
                    }
            }
        }
    }

    private fun getPlaylists() {
        viewModelScope.launch {
            state = state.copy(
                playlists = repository.getPlaylists("")
            )
        }
    }

    private fun updatePlaylistSongs(
        playlistId: Int
    ) {
        viewModelScope.launch {
            val playlist = repository.getPlaylist(playlistId)
            val songsList: MutableList<Int> = playlist.songs.toMutableList()
            songsList.addAll(state.selectedSongs)
            repository.updatePlaylistSongs(playlistId, songsList.toSet())
            state = state.copy(
                selectedSongs = emptyList()
            )
        }
    }
}