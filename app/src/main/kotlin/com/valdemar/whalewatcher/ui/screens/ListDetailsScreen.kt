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

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.valdemar.whalewatcher.ui.ListDetailsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListDetailsScreen(
    listId: Long,
    onNavigateBack: () -> Unit,
    onNavigateToImage: (String, String) -> Unit = { _, _ -> },
    viewModel: ListDetailsViewModel = hiltViewModel()
) {
    val collectionWithImages by viewModel.collectionState.collectAsState()

    LaunchedEffect(listId) {
        viewModel.loadCollection(listId)
    }

    val collection = collectionWithImages?.collection
    val images = collectionWithImages?.images ?: emptyList()
    val listName = collection?.name ?: "Loading..."

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(listName, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onBackground,
                ),
            )
        },
        containerColor = MaterialTheme.colorScheme.background,
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {
            collection?.let { coll ->
                if (coll.isSystem) {
                    androidx.compose.material3.ElevatedCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        colors = androidx.compose.material3.CardDefaults.elevatedCardColors(
                            containerColor = MaterialTheme.colorScheme.secondaryContainer
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                androidx.compose.material3.Icon(
                                    imageVector = com.valdemar.whalewatcher.ui.screens.getIconForName(coll.iconName),
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.onSecondaryContainer
                                )
                                Text(
                                    text = coll.name,
                                    style = MaterialTheme.typography.titleMedium,
                                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                                    fontWeight = FontWeight.Bold
                                )
                                androidx.compose.foundation.layout.Spacer(modifier = Modifier.weight(1f))
                                androidx.compose.material3.Badge(
                                    containerColor = MaterialTheme.colorScheme.primary,
                                    contentColor = MaterialTheme.colorScheme.onPrimary
                                ) {
                                    Text("Read-Only", modifier = Modifier.padding(horizontal = 4.dp))
                                }
                            }
                            if (coll.description.isNotEmpty()) {
                                androidx.compose.foundation.layout.Spacer(modifier = Modifier.padding(4.dp))
                                Text(
                                    text = coll.description,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSecondaryContainer
                                )
                            }
                        }
                    }
                }
            }

            if (images.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize().weight(1f), contentAlignment = Alignment.Center) {
                    Text(
                        text = "No images found in this collection.",
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(
                        start = 16.dp, 
                        end = 16.dp, 
                        top = if (collection?.isSystem == true) 0.dp else 16.dp, 
                        bottom = 16.dp
                    ),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    items(images) { image ->
                        Row(modifier = Modifier.fillMaxWidth()) {
                            ImageCard(
                                name = image.name,
                                namespace = image.namespace,
                                description = image.description,
                                starCount = image.stars,
                                pullCount = image.pullCount,
                                isFavorite = image.isFavorite,
                                onFavoriteClick = { viewModel.toggleFavorite(image) },
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
