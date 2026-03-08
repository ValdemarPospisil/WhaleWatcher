package com.valdemar.whalewatcher.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.valdemar.whalewatcher.data.network.DockerTag
import com.valdemar.whalewatcher.data.network.RepositoryInfo
import com.valdemar.whalewatcher.ui.ImageDetailUiState
import com.valdemar.whalewatcher.ui.ImageDetailViewModel
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageDetailScreen(
    namespace: String,
    repository: String,
    onNavigateBack: () -> Unit = {},
    viewModel: ImageDetailViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val tabs = listOf("Info", "Tags")
    val context = LocalContext.current

    LaunchedEffect(namespace, repository) {
        viewModel.loadDetails(namespace, repository)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("$namespace/$repository") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { /* Handle quick favorite */ }) {
                        Icon(Icons.Default.FavoriteBorder, contentDescription = "Favorite")
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
        bottomBar = {
            if (uiState is ImageDetailUiState.Success) {
                BottomAppBar(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        IconButton(onClick = { /* Will open bottom sheet */ }) {
                            Text("💾", style = MaterialTheme.typography.headlineMedium)
                        }
                        IconButton(
                            onClick = {
                                val info = (uiState as ImageDetailUiState.Success).repositoryInfo
                                val intent =
                                    Intent(
                                        Intent.ACTION_VIEW,
                                        Uri.parse("https://hub.docker.com/r/${info.namespace}/${info.name}"),
                                    )
                                context.startActivity(intent)
                            },
                        ) {
                            Text("🌐", style = MaterialTheme.typography.headlineMedium)
                        }
                    }
                }
            }
        },
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding).fillMaxSize()) {
            TabRow(selectedTabIndex = selectedTabIndex) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = { selectedTabIndex = index },
                        text = { Text(title) },
                    )
                }
            }

            when (val state = uiState) {
                is ImageDetailUiState.Idle, is ImageDetailUiState.Loading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
                is ImageDetailUiState.Error -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("Error: ${state.message}", color = MaterialTheme.colorScheme.error)
                    }
                }
                is ImageDetailUiState.Success -> {
                    if (selectedTabIndex == 0) {
                        InfoTabContent(info = state.repositoryInfo)
                    } else {
                        TagsTabContent(tags = state.tags)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun InfoTabContent(info: RepositoryInfo) {
    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
    ) {
        // Dynamic Indicators
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth(),
        ) {
            if (info.isPrivate) {
                SuggestionChip(
                    onClick = {},
                    label = { Text("Private") },
                    icon = {
                        Icon(
                            Icons.Default.Warning,
                            contentDescription = null,
                            modifier = Modifier.height(16.dp),
                        )
                    },
                )
            } else {
                SuggestionChip(
                    onClick = {},
                    label = { Text("Public") },
                    icon = { Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.height(16.dp)) },
                )
            }
            if (info.statusDescription != null && info.statusDescription.isNotBlank()) {
                SuggestionChip(
                    onClick = {},
                    label = { Text(info.statusDescription.replaceFirstChar { it.uppercase() }) },
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Dates
        Text(
            text = "Last Updated",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Text(
            text = formatIsoDate(info.lastUpdated),
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurface,
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Registered: ${formatIsoDate(info.dateRegistered)}",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Stats
        Row(horizontalArrangement = Arrangement.spacedBy(24.dp)) {
            Column {
                Text("⬇️ Pulls", style = MaterialTheme.typography.labelSmall)
                Text(
                    text = formatCount(info.pullCount),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                )
            }
            Column {
                Text("⭐ Stars", style = MaterialTheme.typography.labelSmall)
                Text(
                    text = formatCount(info.starCount),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Description
        Text("Description", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = info.fullDescription ?: info.description ?: "No description provided.",
            style = MaterialTheme.typography.bodyMedium,
        )

        // Add extra space at the bottom to ensure content isn't hidden behind the floating bar
        Spacer(modifier = Modifier.height(80.dp))
    }
}

@Composable
fun TagsTabContent(tags: List<DockerTag>) {
    if (tags.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("No tags available", color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
        ) {
            items(tags) { tag ->
                val sizeInMB = tag.fullSize / (1024 * 1024).toFloat()
                val sizeText = if (sizeInMB > 0) String.format(Locale.US, "%.1f MB", sizeInMB) else "Unknown Size"

                Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                    Text(tag.name, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodyLarge)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        sizeText,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
                HorizontalDivider(color = MaterialTheme.colorScheme.surfaceVariant)
            }
        }
    }
}

private fun formatCount(count: Long): String {
    return if (count >= 1000000) {
        String.format(Locale.US, "%.1fM", count / 1000000.0)
    } else if (count >= 1000) {
        String.format(Locale.US, "%.1fK", count / 1000.0)
    } else {
        count.toString()
    }
}

private fun formatIsoDate(isoString: String?): String {
    if (isoString.isNullOrBlank()) return "Unknown"
    return try {
        val instant = Instant.parse(isoString)
        val formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy", Locale.US).withZone(ZoneId.systemDefault())
        formatter.format(instant)
    } catch (e: Exception) {
        isoString
    }
}
