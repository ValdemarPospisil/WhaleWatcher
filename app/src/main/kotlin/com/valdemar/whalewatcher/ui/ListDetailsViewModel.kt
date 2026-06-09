package com.valdemar.whalewatcher.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.valdemar.whalewatcher.data.local.entities.CollectionWithImages
import com.valdemar.whalewatcher.data.repository.DockerImageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListDetailsViewModel @Inject constructor(
    private val repository: DockerImageRepository
) : ViewModel() {

    private val _collectionState = MutableStateFlow<CollectionWithImages?>(null)
    val collectionState: StateFlow<CollectionWithImages?> = _collectionState.asStateFlow()

    fun loadCollection(id: Long) {
        viewModelScope.launch {
            repository.getCollectionWithImages(id)
                .catch { e ->
                    // Handle error if needed
                }
                .collect { collectionWithImages ->
                    _collectionState.value = collectionWithImages
                }
        }
    }

    fun toggleFavorite(image: com.valdemar.whalewatcher.data.local.entities.DockerImageEntity) {
        viewModelScope.launch {
            repository.toggleFavorite(image)
        }
    }
}
