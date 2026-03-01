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
import com.valdemar.whalewatcher.ui.models.DummyCategory
import com.valdemar.whalewatcher.ui.models.DummyData

@Composable
fun HomeScreen(onNavigateToList: (String) -> Unit) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = 16.dp)
    ) {
        // App Header
        item {
            Text(
                text = "WhaleWatcher",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Favorites Row
        item {
            CategoryRow(
                category = DummyData.favorites,
                onViewAllClick = { onNavigateToList(DummyData.favorites.title) },
                onImageClick = { /* Will navigate to image details later */ }
            )
            Spacer(modifier = Modifier.height(24.dp))
        }

        // System Categories
        items(DummyData.systemCategories) { category ->
            CategoryRow(
                category = category,
                onViewAllClick = { onNavigateToList(category.title) },
                onImageClick = { /* Will navigate to image details later */ }
            )
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun CategoryRow(
    category: DummyCategory,
    onViewAllClick: () -> Unit,
    onImageClick: (String) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = category.title,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = "View All",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable(onClick = onViewAllClick)
            )
        }
        
        Spacer(modifier = Modifier.height(12.dp))
        
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(category.images) { image ->
                ImageCard(
                    image = image,
                    onClick = { onImageClick(image.name) }
                )
            }
        }
    }
}
