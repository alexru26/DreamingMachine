package com.alexru.presentation.discover

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexru.domain.repository.DiscographyRepository
import com.alexru.domain.resource.Result
import com.alexru.presentation.util.asErrorUiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for Discover screen
 */
@HiltViewModel
class DiscoverScreenViewModel @Inject constructor(
    private val repository: DiscographyRepository
): ViewModel() {

    var state by mutableStateOf(DiscoverState())

    private var searchJob: Job? = null

    init {
        getAlbums()
    }

    fun onEvent(event: DiscoverEvent) {
        when(event) {
            is DiscoverEvent.Refresh -> {
                state = state.copy(
                    isRefreshing = true
                )
                getAlbums(fetchFromRemote = true)
            }
            is DiscoverEvent.OnSearchQueryChange -> {
                state = state.copy(searchQuery = event.query)
                searchJob?.cancel()
                searchJob = viewModelScope.launch {
                    delay(500L)
                    getAlbums()
                }
            }
        }
    }

    private fun getAlbums(
        query: String = state.searchQuery.lowercase(),
        fetchFromRemote: Boolean = false
    ) {
        viewModelScope.launch {
            repository.getAlbums(fetchFromRemote, query)
                .collect { result ->
                    when(result) {
                        is Result.Success -> {
                            state = state.copy(
                                albums = result.data,
                            )
                        }
                        is Result.Error -> {
                            state = state.copy(
                                error = result.asErrorUiText()
                            )
                        }
                        is Result.Loading -> {
//                            state = state.copy(
//                                isLoading = result.isLoading
//                            )
                            if(!result.isLoading) {
                                state = state.copy(
                                    isRefreshing = false
                                )
                            }
                        }
                    }
                }
        }
    }

}