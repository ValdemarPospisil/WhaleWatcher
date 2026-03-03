package com.valdemar.whalewatcher.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.valdemar.whalewatcher.data.repository.DockerImageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class MainUiState {
    object Idle : MainUiState()

    object Loading : MainUiState()

    data class Success(val imageName: String) : MainUiState()

    data class Error(val message: String) : MainUiState()
}

@HiltViewModel
class MainViewModel
    @Inject
    constructor(
        private val repository: DockerImageRepository,
    ) : ViewModel() {
        private val _uiState = MutableStateFlow<MainUiState>(MainUiState.Idle)
        val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()

        fun fetchImages() {
            _uiState.value = MainUiState.Loading
            viewModelScope.launch {
                val result = repository.getRepositories("library")
                result.onSuccess { response ->
                    val firstName = response.results.firstOrNull()?.name
                    if (firstName != null) {
                        _uiState.value = MainUiState.Success(firstName)
                    } else {
                        _uiState.value = MainUiState.Error("No images found")
                    }
                }.onFailure { error ->
                    _uiState.value = MainUiState.Error(error.message ?: "Unknown error")
                }
            }
        }
    }
