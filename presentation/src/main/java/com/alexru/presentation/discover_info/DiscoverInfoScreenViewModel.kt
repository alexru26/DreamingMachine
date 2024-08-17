package com.alexru.presentation.discover_info

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
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for Discover Info screen
 */
@HiltViewModel
class DiscoverInfoScreenViewModel @Inject constructor(
    private val repository: DiscographyRepository,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {

    var state by mutableStateOf(DiscoverInfoState())

    init {
        getSongs()
    }

    fun onEvent(vararg events: DiscoverInfoEvent) {
        events.forEach { event ->
            when(event) {
                is DiscoverInfoEvent.SelectSong -> {
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
                is DiscoverInfoEvent.DeselectAllSongs -> {
                    state = state.copy(
                        selectedSongs = emptyList()
                    )
                }
                is DiscoverInfoEvent.OpenSaveToPlaylistDialog -> {
                    viewModelScope.launch {
                        state = state.copy(
                            playlists = repository.getPlaylists(""),
                            openSaveToPlaylistDialog = true
                        )
                    }
                }
                is DiscoverInfoEvent.CloseSaveToPlaylistDialog -> {
                    state = state.copy(
                        openSaveToPlaylistDialog = false
                    )
                }
                is DiscoverInfoEvent.SaveToPlaylist -> updatePlaylistSongs(event.playlistId)
            }
        }
    }

    private fun getSongs() {
        viewModelScope.launch {
            val albumId = savedStateHandle.get<Int>("albumId") ?: return@launch

            val albums = async { repository.getAlbum(albumId) }
            val songs = async { repository.getSongs(albumId) }

            state = state.copy(
                album = albums.await()
            )
            songs.await()
                .collect { result ->
                    when(result) {
                        is Result.Success -> {
                            state = state.copy(
                                songs = result.data
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