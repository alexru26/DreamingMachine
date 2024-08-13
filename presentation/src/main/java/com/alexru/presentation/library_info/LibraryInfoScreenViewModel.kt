package com.alexru.presentation.library_info

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexru.domain.model.Playlist
import com.alexru.domain.model.Resource
import com.alexru.domain.repository.DiscographyRepository
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

            val result = repository.getPlaylist(playlistId)
            when(result) {
                is Resource.Success -> {
                    state = state.copy(
                        playlist = result.data
                    )

                }
                is Resource.Error -> {
                    state = state.copy(
                        error = result.message
                    )
                    return@launch
                }
                else -> Unit
            }
            result.data?.let { playlist ->
                repository.getSongs(playlist.songs)
                    .collect { result ->
                        when(result) {
                            is Resource.Success -> {
                                result.data?.let { listings ->
                                    state = state.copy(
                                        songs = listings,
                                    )
                                }
                            }
                            is Resource.Error -> {
                                state = state.copy(
                                    error = result.message
                                )
                            }
                            is Resource.Loading -> {
                                state = state.copy(
                                    isLoading = result.isLoading
                                )
                            }
                        }
                    }
            }
        }
    }

    private fun getPlaylists() {
        viewModelScope.launch {
            repository.getPlaylists("")
                .collect { result ->
                    when(result) {
                        is Resource.Success -> {
                            result.data?.let { listings ->
                                state = state.copy(
                                    playlists = listings
                                )
                            }
                        }
                        else -> Unit
                    }
                }
        }
    }

    private fun updatePlaylistSongs(
        playlistId: Int
    ) {
        viewModelScope.launch {
            var playlist: Playlist? = null
            when(val result = repository.getPlaylist(playlistId)) {
                is Resource.Success -> {
                    result.data?.let {
                        playlist = it
                    }
                }
                else -> Unit
            }
            playlist?.let {
                val songsList: MutableList<Int> = it.songs.toMutableList()
                songsList.addAll(state.selectedSongs)
                repository.updatePlaylistSongs(it.id, songsList.toSet())
            }
            state = state.copy(
                selectedSongs = emptyList()
            )
        }
    }
}