package com.valdemar.whalewatcher.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.valdemar.whalewatcher.data.local.entities.CollectionWithImages
import com.valdemar.whalewatcher.data.local.entities.DockerImageEntity
import com.valdemar.whalewatcher.data.repository.DockerImageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: DockerImageRepository
) : ViewModel() {

    private val _collectionsState = MutableStateFlow<List<CollectionWithImages>>(emptyList())
    val collectionsState: StateFlow<List<CollectionWithImages>> = _collectionsState.asStateFlow()

    init {
        viewModelScope.launch {
            repository.ensureFavoritesCollectionExists()
            loadCollections()
        }
    }

    private fun loadCollections() {
        viewModelScope.launch {
            repository.getAllCollectionsWithImages()
                .catch { e ->
                    // Handle error if needed
                }
                .collect { collections ->
                    _collectionsState.value = collections
                }
        }
    }

    fun toggleFavorite(image: DockerImageEntity) {
        viewModelScope.launch {
            repository.toggleFavorite(image)
        }
    }
}
