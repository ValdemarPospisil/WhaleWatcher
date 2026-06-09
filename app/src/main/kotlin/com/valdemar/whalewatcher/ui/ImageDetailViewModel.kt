package com.valdemar.whalewatcher.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.valdemar.whalewatcher.data.local.entities.DockerImageEntity
import com.valdemar.whalewatcher.data.network.DockerTag
import com.valdemar.whalewatcher.data.network.RepositoryInfo
import com.valdemar.whalewatcher.data.repository.DockerImageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class ImageDetailUiState {
    object Idle : ImageDetailUiState()

    object Loading : ImageDetailUiState()

    data class Success(val repositoryInfo: RepositoryInfo, val tags: List<DockerTag>, val isFavorite: Boolean = false) : ImageDetailUiState()

    data class Error(val message: String) : ImageDetailUiState()
}

@HiltViewModel
class ImageDetailViewModel
    @Inject
    constructor(
        private val repository: DockerImageRepository,
    ) : ViewModel() {
        private val _uiState = MutableStateFlow<ImageDetailUiState>(ImageDetailUiState.Idle)
        val uiState: StateFlow<ImageDetailUiState> = _uiState.asStateFlow()

        fun loadDetails(
            namespace: String,
            repositoryName: String,
        ) {
            viewModelScope.launch {
                _uiState.value = ImageDetailUiState.Loading

                val detailsResult = repository.getImageDetails(namespace, repositoryName)
                if (detailsResult.isFailure) {
                    _uiState.value =
                        ImageDetailUiState.Error(
                            detailsResult.exceptionOrNull()?.message ?: "Failed to load repository details",
                        )
                    return@launch
                }

                val tagsResult = repository.getImageTags(namespace, repositoryName)
                if (tagsResult.isFailure) {
                    _uiState.value =
                        ImageDetailUiState.Error(
                            tagsResult.exceptionOrNull()?.message ?: "Failed to load tags",
                        )
                    return@launch
                }

                val isFav = repository.isFavorite(repositoryName, namespace)

                _uiState.value =
                    ImageDetailUiState.Success(
                        repositoryInfo = detailsResult.getOrNull()!!,
                        tags = tagsResult.getOrNull()?.results ?: emptyList(),
                        isFavorite = isFav
                    )
            }
        }

        fun toggleFavorite() {
            val state = _uiState.value
            if (state is ImageDetailUiState.Success) {
                viewModelScope.launch {
                    val info = state.repositoryInfo
                    val image = DockerImageEntity(
                        name = info.name,
                        namespace = info.namespace,
                        description = info.description ?: "",
                        pullCount = info.pullCount.toString(),
                        stars = info.starCount.toInt(),
                        isFavorite = false // repository will fetch existing to toggle
                    )
                    val isFavNow = !state.isFavorite
                    repository.toggleFavorite(image)
                    _uiState.value = state.copy(isFavorite = isFavNow)
                }
            }
        }

        fun addToCollection(collectionId: Long) {
            val state = _uiState.value
            if (state is ImageDetailUiState.Success) {
                viewModelScope.launch {
                    val info = state.repositoryInfo
                    val image = DockerImageEntity(
                        name = info.name,
                        namespace = info.namespace,
                        description = info.description ?: "",
                        pullCount = info.pullCount.toString(),
                        stars = info.starCount.toInt(),
                        isFavorite = state.isFavorite
                    )
                    repository.addImageToCollection(image, collectionId)
                }
            }
        }
    }
