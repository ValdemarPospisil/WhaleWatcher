package com.valdemar.whalewatcher.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.valdemar.whalewatcher.data.network.DockerSearchResult
import com.valdemar.whalewatcher.data.repository.DockerImageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class SearchUiState {
    object Idle : SearchUiState()
    object Loading : SearchUiState()
    object Empty : SearchUiState()
    data class Success(
        val results: List<DockerSearchResult>,
        val totalCount: Int,
        val isFetchingNextPage: Boolean = false,
        val hasNextPage: Boolean = false
    ) : SearchUiState()
    data class Error(val message: String) : SearchUiState()
}

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: DockerImageRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<SearchUiState>(SearchUiState.Idle)
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private var currentPage = 1
    private var hasNextPage = false

    init {
        viewModelScope.launch {
            _searchQuery
                .debounce(500L)
                .distinctUntilChanged()
                .collect { query ->
                    if (query.isBlank()) {
                        _uiState.value = SearchUiState.Idle
                    } else {
                        performSearch(query, 1)
                    }
                }
        }
    }

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }

    private suspend fun performSearch(query: String, page: Int) {
        if (page == 1) {
            _uiState.value = SearchUiState.Loading
        } else {
            val currentState = _uiState.value
            if (currentState is SearchUiState.Success) {
                _uiState.value = currentState.copy(isFetchingNextPage = true)
            }
        }

        val result = repository.searchImages(query, page)
        result.onSuccess { response ->
            val isNextUrlPresent = response.next != null
            hasNextPage = isNextUrlPresent
            currentPage = page

            if (response.results.isEmpty() && page == 1) {
                _uiState.value = SearchUiState.Empty
            } else {
                val currentResults = if (page == 1) emptyList() else {
                    (_uiState.value as? SearchUiState.Success)?.results ?: emptyList()
                }
                
                _uiState.value = SearchUiState.Success(
                    results = currentResults + response.results,
                    totalCount = response.count,
                    isFetchingNextPage = false,
                    hasNextPage = isNextUrlPresent
                )
            }
        }.onFailure { error ->
            if (page == 1) {
                _uiState.value = SearchUiState.Error(error.message ?: "Unknown error")
            } else {
                // Restore state and just stop fetching
                val currentState = _uiState.value
                if (currentState is SearchUiState.Success) {
                    _uiState.value = currentState.copy(isFetchingNextPage = false)
                }
            }
        }
    }

    fun loadNextPage() {
        if (!hasNextPage) return
        
        val currentState = _uiState.value
        if (currentState is SearchUiState.Success && !currentState.isFetchingNextPage) {
            viewModelScope.launch {
                performSearch(_searchQuery.value, currentPage + 1)
            }
        }
    }
}
