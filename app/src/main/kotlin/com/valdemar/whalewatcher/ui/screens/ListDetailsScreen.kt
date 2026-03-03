package com.valdemar.whalewatcher.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.valdemar.whalewatcher.ui.components.ImageCard
import com.valdemar.whalewatcher.ui.models.DummyData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListDetailsScreen(
    listName: String,
    onNavigateBack: () -> Unit,
    onNavigateToImage: (String, String) -> Unit = { _, _ -> }
) {
    // Resolve the category based on the listName from dummy data
    val category =
        DummyData.systemCategories.find { it.title == listName }
            ?: DummyData.customLists.find { it.title == listName }
            ?: if (DummyData.favorites.title == listName) DummyData.favorites else null

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(listName, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors =
                    TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.background,
                        titleContentColor = MaterialTheme.colorScheme.primary,
                        navigationIconContentColor = MaterialTheme.colorScheme.onBackground,
                    ),
            )
        },
        containerColor = MaterialTheme.colorScheme.background,
    ) { innerPadding ->
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
        ) {
            if (category == null || category.images.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        text = "No images found in this list.",
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    items(category.images) { image ->
                        // Reuse our ImageCard but modify it to span full width for vertical list
                        Row(modifier = Modifier.fillMaxWidth()) {
                            ImageCard(
                                image = image,
                                modifier = Modifier.weight(1f),
                                onClick = { onNavigateToImage(image.namespace, image.name) },
                            )
                        }
                    }
                }
            }
        }
    }
}
