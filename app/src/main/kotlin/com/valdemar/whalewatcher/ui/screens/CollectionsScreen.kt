package com.valdemar.whalewatcher.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextButton
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.valdemar.whalewatcher.data.local.entities.CollectionEntity
import com.valdemar.whalewatcher.ui.CollectionsViewModel

@Composable
fun CollectionsScreen(
    onNavigateToList: (Long) -> Unit,
    viewModel: CollectionsViewModel = hiltViewModel()
) {
    val collections by viewModel.collectionsState.collectAsState()

    CollectionsScreen(
        collections = collections,
        onCreateCollection = { name, desc, icon -> viewModel.createCollection(name, desc, icon) },
        onCollectionClick = { onNavigateToList(it) }
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CollectionsScreen(
    collections: List<CollectionEntity>,
    onCreateCollection: (String, String, String) -> Unit,
    onCollectionClick: (Long) -> Unit
) {
    var showCreateDialog by remember { mutableStateOf(false) }

    val availableIcons = listOf(
        "List" to Icons.AutoMirrored.Filled.List,
        "Favorite" to Icons.Default.Favorite,
        "Star" to Icons.Default.Star,
        "Build" to Icons.Default.Build,
        "Face" to Icons.Default.Face,
        "Home" to Icons.Default.Home,
        "Lock" to Icons.Default.Lock,
        "Person" to Icons.Default.Person,
        "Settings" to Icons.Default.Settings,
        "ShoppingCart" to Icons.Default.ShoppingCart,
        "ThumbUp" to Icons.Default.ThumbUp,
        "Warning" to Icons.Default.Warning,
        "Folder" to Icons.Default.Folder,
        "DateRange" to Icons.Default.DateRange,
        "CheckCircle" to Icons.Default.CheckCircle
    )

    if (showCreateDialog) {
        var name by remember { mutableStateOf("") }
        var description by remember { mutableStateOf("") }
        var iconName by remember { mutableStateOf("List") }
        var attemptedSubmit by remember { mutableStateOf(false) }

        val isNameValid = name.isNotBlank()

        AlertDialog(
            onDismissRequest = { showCreateDialog = false },
            title = { Text("Create new collection") },
            text = {
                Column {
                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it; attemptedSubmit = false },
                        label = { Text("Name") },
                        isError = attemptedSubmit && !isNameValid,
                        singleLine = true
                    )
                    if (attemptedSubmit && !isNameValid) {
                        Text("Name cannot be empty", color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = description,
                        onValueChange = { description = it },
                        label = { Text("Description") },
                        maxLines = 3
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("Select Icon", style = MaterialTheme.typography.labelLarge)
                    Spacer(modifier = Modifier.height(8.dp))
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        availableIcons.forEach { (iName, vector) ->
                            IconButton(onClick = { iconName = iName }) {
                                Icon(
                                    imageVector = vector,
                                    contentDescription = iName,
                                    tint = if (iconName == iName) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (isNameValid) {
                            onCreateCollection(name, description, iconName)
                            showCreateDialog = false
                        } else {
                            attemptedSubmit = true
                        }
                    }
                ) {
                    Text("Create")
                }
            },
            dismissButton = {
                TextButton(onClick = { showCreateDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showCreateDialog = true },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
            ) {
                Icon(Icons.Filled.Add, "Create Collection")
            }
        },
        containerColor = MaterialTheme.colorScheme.background,
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(vertical = 16.dp),
        ) {
            item {
                Text(
                    text = "Collections",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            items(collections) { collection ->
                val iconVector = if (collection.isFavorite) {
                    Icons.Default.Favorite
                } else {
                    availableIcons.find { it.first == collection.iconName }?.second ?: Icons.AutoMirrored.Filled.List
                }
                
                CollectionsListItem(
                    title = collection.name,
                    description = collection.description,
                    icon = iconVector,
                    iconTint = if (collection.isFavorite) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.secondary,
                    onClick = { onCollectionClick(collection.id) },
                )
            }
        }
    }
}

@Composable
fun CollectionsListItem(
    title: String,
    description: String,
    icon: ImageVector,
    iconTint: androidx.compose.ui.graphics.Color,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = iconTint,
            modifier = Modifier.size(24.dp),
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground,
            )
            if (description.isNotBlank()) {
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    }
}
