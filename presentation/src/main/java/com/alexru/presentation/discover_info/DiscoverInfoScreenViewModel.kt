package com.alexru.presentation.discover_info

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
        getPlaylists()
        getSongs()
    }

    fun onEvent(event: DiscoverInfoEvent) {
        when(event) {
            is DiscoverInfoEvent.SaveToPlaylist -> {
                updatePlaylistSongs(event.playlistId, event.songId)
            }
        }
    }

    private fun getSongs() {
        viewModelScope.launch {
            val albumId = savedStateHandle.get<Int>("albumId") ?: return@launch

            val albums = async { repository.getAlbum(albumId) }
            val songs = async { repository.getSongs(albumId) }

            when(val result = albums.await()) {
                is Resource.Success -> {
                    state = state.copy(
                        album = result.data
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
            songs.await()
                .collect { result ->
                    when(result) {
                        is Resource.Success -> {
                            result.data?.let { listings ->
                                state = state.copy(
                                    songs = listings
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
        playlistId: Int,
        songId: Int
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
                songsList.add(songId)
                repository.updatePlaylistSongs(it.id, songsList.toList())
            }
        }
    }
}