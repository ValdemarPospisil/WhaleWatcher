package com.valdemar.whalewatcher.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.valdemar.whalewatcher.ui.components.ImageCard
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.valdemar.whalewatcher.data.local.entities.CollectionWithImages
import com.valdemar.whalewatcher.data.local.entities.DockerImageEntity
import com.valdemar.whalewatcher.ui.HomeViewModel

@Composable
fun HomeScreen(
    onNavigateToList: (Long) -> Unit,
    onNavigateToImage: (String, String) -> Unit = { _, _ -> },
    viewModel: HomeViewModel = hiltViewModel()
) {
    val collectionsWithImages by viewModel.collectionsState.collectAsState()

    val favorites = collectionsWithImages.firstOrNull { it.collection.isFavorite }
    val systemCategories = collectionsWithImages.filter { it.collection.isSystem && !it.collection.isFavorite }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = 16.dp),
    ) {
        // App Header
        item {
            Text(
                text = "WhaleWatcher",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Favorites Row
        if (favorites != null && favorites.images.isNotEmpty()) {
            item {
                CategoryRow(
                    collectionWithImages = favorites,
                    onViewAllClick = { onNavigateToList(favorites.collection.id) },
                    onImageClick = onNavigateToImage,
                    onFavoriteClick = { viewModel.toggleFavorite(it) }
                )
                Spacer(modifier = Modifier.height(24.dp))
            }
        }

        // System Categories
        items(systemCategories) { category ->
            if (category.images.isNotEmpty()) {
                CategoryRow(
                    collectionWithImages = category,
                    onViewAllClick = { onNavigateToList(category.collection.id) },
                    onImageClick = onNavigateToImage,
                    onFavoriteClick = { viewModel.toggleFavorite(it) }
                )
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
fun CategoryRow(
    collectionWithImages: CollectionWithImages,
    onViewAllClick: () -> Unit,
    onImageClick: (String, String) -> Unit,
    onFavoriteClick: (DockerImageEntity) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = collectionWithImages.collection.name,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground,
            )
            Text(
                text = "View All",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable(onClick = onViewAllClick),
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            items(collectionWithImages.images) { image ->
                ImageCard(
                    name = image.name,
                    namespace = image.namespace,
                    description = image.description,
                    starCount = image.stars,
                    pullCount = image.pullCount,
                    isFavorite = image.isFavorite,
                    onFavoriteClick = { onFavoriteClick(image) },
                    onClick = { onImageClick(image.namespace, image.name) },
                )
            }
        }
    }
}
