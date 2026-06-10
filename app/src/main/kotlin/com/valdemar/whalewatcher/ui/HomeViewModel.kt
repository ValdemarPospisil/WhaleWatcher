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

    private val _systemCategoriesState = MutableStateFlow<List<CollectionWithImages>>(emptyList())
    val systemCategoriesState: StateFlow<List<CollectionWithImages>> = _systemCategoriesState.asStateFlow()

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
                    _systemCategoriesState.value = collections.filter { it.collection.isSystem && !it.collection.isFavorite }
                }
        }
    }

    fun toggleFavorite(image: DockerImageEntity) {
        viewModelScope.launch {
            repository.toggleFavorite(image)
        }
    }
}
