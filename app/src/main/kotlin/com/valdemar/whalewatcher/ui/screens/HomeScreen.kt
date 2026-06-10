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
import com.valdemar.whalewatcher.data.local.entities.CollectionEntity
import com.valdemar.whalewatcher.data.local.entities.CollectionWithImages
import com.valdemar.whalewatcher.data.local.entities.DockerImageEntity
import com.valdemar.whalewatcher.ui.HomeViewModel

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.size
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*

@Composable
fun HomeScreen(
    onNavigateToList: (Long) -> Unit,
    onNavigateToImage: (String, String) -> Unit = { _, _ -> },
    viewModel: HomeViewModel = hiltViewModel()
) {
    val collectionsWithImages by viewModel.collectionsState.collectAsState()
    val systemCategories by viewModel.systemCategoriesState.collectAsState()

    val favorites = collectionsWithImages.firstOrNull { it.collection.isFavorite }

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // App Header
        item(span = { GridItemSpan(maxLineSpan) }) {
            Text(
                text = "WhaleWatcher",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 8.dp),
            )
        }

        // Favorites Row
        if (favorites != null && favorites.images.isNotEmpty()) {
            item(span = { GridItemSpan(maxLineSpan) }) {
                CategoryRow(
                    collectionWithImages = favorites,
                    onViewAllClick = { onNavigateToList(favorites.collection.id) },
                    onImageClick = onNavigateToImage,
                    onFavoriteClick = { viewModel.toggleFavorite(it) }
                )
            }
        }

        // Categories Header
        item(span = { GridItemSpan(maxLineSpan) }) {
            Text(
                text = "Categories",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
            )
        }

        // Categories Grid
        items(systemCategories) { category ->
            CategoryGridItem(
                category = category.collection,
                onClick = { onNavigateToList(category.collection.id) }
            )
        }
    }
}

@Composable
fun CategoryGridItem(
    category: CollectionEntity,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = getIconForName(category.iconName),
                contentDescription = category.name,
                modifier = Modifier.size(48.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = category.name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        }
    }
}

fun getIconForName(iconName: String) = when (iconName) {
    "Storage" -> Icons.Default.Storage
    "Public" -> Icons.Default.Public
    "Build" -> Icons.Default.Build
    "Analytics" -> Icons.Default.Analytics
    "Security" -> Icons.Default.Security
    "Insights" -> Icons.Default.Insights
    "Computer" -> Icons.Default.Computer
    "Mail" -> Icons.Default.Mail
    "Router" -> Icons.Default.Router
    "Code" -> Icons.Default.Code
    "Favorite" -> Icons.Default.Favorite
    else -> Icons.Default.Folder
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
                .fillMaxWidth(),
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
