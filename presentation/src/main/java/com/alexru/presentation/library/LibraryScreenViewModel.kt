package com.alexru.presentation.library

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexru.domain.model.Playlist
import com.alexru.domain.model.Resource
import com.alexru.domain.repository.DiscographyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for Library screen
 */
@HiltViewModel
class LibraryScreenViewModel @Inject constructor(
    private val repository: DiscographyRepository
): ViewModel() {

    var state by mutableStateOf(LibraryState())

    private var searchJob: Job? = null

    init {
        getPlaylists()
    }

    fun onEvent(vararg events: LibraryEvent) {
        events.forEach { event ->
            when(event) {
                is LibraryEvent.SelectPlaylist -> {
                    state = if(event.selected) {
                        state.copy(
                            selectedPlaylists = state.selectedPlaylists-event.playlistId
                        )
                    } else {
                        state.copy(
                            selectedPlaylists = state.selectedPlaylists+event.playlistId
                        )
                    }
                }
                is LibraryEvent.DeselectAllSongs -> {
                    state = state.copy(
                        selectedPlaylists = emptyList()
                    )
                }
                is LibraryEvent.CreatePlaylist -> createPlaylist(event.playlist)
                is LibraryEvent.DeletePlaylist -> deletePlaylist()
                is LibraryEvent.OnSearchQueryChange -> {
                    state = state.copy(
                        searchQuery = event.query
                    )
                    searchJob?.cancel()
                    searchJob = viewModelScope.launch {
                        delay(500L)
                        getPlaylists()
                    }
                }
            }
        }
    }

    private fun getPlaylists(
        query: String = state.searchQuery.lowercase(),
    ) {
        viewModelScope.launch {
            repository.getPlaylists(query)
                .collect { result ->
                    when(result) {
                        is Resource.Success -> {
                            result.data?.let { listings ->
                                state = state.copy(
                                    playlists = listings,
                                )
                            }
                        }
                        is Resource.Error -> {
                            state = state.copy(
                                error = result.message
                            )
                        }
                        is Resource.Loading -> {
//                            state = state.copy(
//                                isLoading = result.isLoading
//                            )
                        }
                    }
                }
        }
    }

    private fun createPlaylist(
        playlist: Playlist
    ) {
        viewModelScope.launch {
            repository.createPlaylist(playlist)
            getPlaylists()
        }
    }

    private fun deletePlaylist() {
        viewModelScope.launch {
            state.selectedPlaylists.forEach {
                repository.deletePlaylist(it)
            }
            state = state.copy(
                selectedPlaylists = emptyList()
            )
            getPlaylists()
        }
    }
}