package com.valdemar.whalewatcher.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.valdemar.whalewatcher.data.local.entities.CollectionEntity
import com.valdemar.whalewatcher.data.repository.DockerImageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CollectionsViewModel @Inject constructor(
    private val repository: DockerImageRepository
) : ViewModel() {

    private val _collectionsState = MutableStateFlow<List<CollectionEntity>>(emptyList())
    val collectionsState: StateFlow<List<CollectionEntity>> = _collectionsState.asStateFlow()

    init {
        loadCollections()
    }

    private fun loadCollections() {
        viewModelScope.launch {
            repository.getAllCollections()
                .catch { e ->
                    // Handle error if needed
                }
                .collect { collections ->
                    _collectionsState.value = collections
                }
        }
    }

    fun createCollection(name: String, description: String, iconName: String) {
        viewModelScope.launch {
            repository.createCollection(name, description, iconName)
        }
    }
}
