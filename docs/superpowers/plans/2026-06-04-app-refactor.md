# App Visual and Navigation Refactor Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Refactor WhaleWatcher UI to a deep dark theme, fix navigation routing, and move static dummy data into MainViewModel to support reactive state like "Favorites".

**Architecture:** Use `MainViewModel` to hold and expose `DummyData` via `StateFlow`. Standardize `ImageCard` to support favorites and status colors. Fix `BottomNavigationBar` state.

**Tech Stack:** Kotlin, Jetpack Compose, Hilt (ViewModel), JUnit

---

### Task 1: Deep Dark Theme Colors

**Files:**
- Modify: `app/src/main/kotlin/com/valdemar/whalewatcher/ui/theme/Color.kt`
- Modify: `app/src/main/kotlin/com/valdemar/whalewatcher/ui/theme/Theme.kt`

- [ ] **Step 1: Implement deep dark colors**

Modify `Color.kt`:
```kotlin
package com.valdemar.whalewatcher.ui.theme

import androidx.compose.ui.graphics.Color

// Deep Dark Theme Colors
val DarkBackground = Color(0xFF09090B)
val DarkSurface = Color(0xFF18181B)
val DarkSurfaceVariant = Color(0xFF27272A)
val DarkPrimary = Color(0xFF0DB7ED)
val DarkSecondary = Color(0xFF38BDF8)
val DarkText = Color(0xFFF8FAFC)
val DarkTextSecondary = Color(0xFF94A3B8)

// Light Theme Colors (unchanged)
val LightBackground = Color(0xFFF8FAFC)
val LightSurface = Color(0xFFFFFFFF)
val LightSurfaceVariant = Color(0xFFF1F5F9)
val LightPrimary = Color(0xFF0DB7ED)
val LightSecondary = Color(0xFF0284C7)
val LightText = Color(0xFF0F172A)
val LightTextSecondary = Color(0xFF475569)

val ErrorRed = Color(0xFFEF4444)
val WarningOrange = Color(0xFFF59E0B)
val SuccessGreen = Color(0xFF10B981)
```

- [ ] **Step 2: Commit**

```bash
git add app/src/main/kotlin/com/valdemar/whalewatcher/ui/theme/Color.kt
git commit -m "style: update to deep dark theme colors and add status colors"
```

---

### Task 2: Update Data Models

**Files:**
- Modify: `app/src/main/kotlin/com/valdemar/whalewatcher/ui/models/DummyData.kt`
- Create: `app/src/test/kotlin/com/valdemar/whalewatcher/ui/models/DummyDataTest.kt`

- [ ] **Step 1: Write the failing test**

Create `DummyDataTest.kt`:
```kotlin
package com.valdemar.whalewatcher.ui.models

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Test

class DummyDataTest {
    @Test
    fun testDataModelsUpdated() {
        val image = DummyDockerImage("ns", "name", "desc", 1, "1k", "2024-01-01", false)
        assertEquals("2024-01-01", image.lastUpdated)
        assertFalse(image.isFavorite)
        
        val category = DummyCategory("id1", "Databases", "desc", "🗄️", listOf(image))
        assertEquals("🗄️", category.emojiIcon)
        
        val collection = DummyCollection("id2", "My Apps", "desc", "📱", listOf(image))
        assertEquals("📱", collection.emojiIcon)
    }
}
```

- [ ] **Step 2: Run test to verify it fails**

Run: `./gradlew testDebugUnitTest --tests "com.valdemar.whalewatcher.ui.models.DummyDataTest"`
Expected: Compilation failure because fields and DummyCollection don't exist.

- [ ] **Step 3: Write minimal implementation**

Modify `DummyData.kt`:
```kotlin
package com.valdemar.whalewatcher.ui.models

import java.util.UUID

data class DummyDockerImage(
    val namespace: String,
    val name: String,
    val description: String,
    val starCount: Int,
    val pullCount: String,
    val lastUpdated: String = "2026-01-01",
    val isFavorite: Boolean = false
) {
    val id: String get() = "$namespace/$name"
}

data class DummyCategory(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val description: String = "",
    val emojiIcon: String = "📁",
    val images: List<DummyDockerImage>
)

data class DummyCollection(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val description: String = "",
    val emojiIcon: String = "📁",
    val images: List<DummyDockerImage>
)

object DummyData {
    val allImages = listOf(
        DummyDockerImage("linuxserver", "plex", "Media server", 1200, "500M+", "2026-05-01"),
        DummyDockerImage("library", "ubuntu", "Ubuntu base", 10000, "1B+", "2025-01-01")
    )
    
    val categories = listOf(
        DummyCategory("cat1", "Databases", "Data storage", "🗄️", allImages)
    )
}
```

- [ ] **Step 4: Run test to verify it passes**

Run: `./gradlew testDebugUnitTest --tests "com.valdemar.whalewatcher.ui.models.DummyDataTest"`
Expected: BUILD SUCCESSFUL

- [ ] **Step 5: Commit**

```bash
git add app/src/main/kotlin/com/valdemar/whalewatcher/ui/models/DummyData.kt app/src/test/kotlin/com/valdemar/whalewatcher/ui/models/DummyDataTest.kt
git commit -m "feat: add missing fields to data models and create DummyCollection"
```

---

### Task 3: Setup App State in MainViewModel

**Files:**
- Modify: `app/src/main/kotlin/com/valdemar/whalewatcher/ui/MainViewModel.kt`
- Create: `app/src/test/kotlin/com/valdemar/whalewatcher/ui/MainViewModelTest.kt`

- [ ] **Step 1: Write the failing test**

Create `app/src/test/kotlin/com/valdemar/whalewatcher/ui/MainViewModelTest.kt`:
```kotlin
package com.valdemar.whalewatcher.ui

import com.valdemar.whalewatcher.data.repository.DockerImageRepository
import org.junit.Assert.assertTrue
import org.junit.Test
import org.mockito.Mockito.mock

class MainViewModelTest {
    @Test
    fun testToggleFavorite() {
        val repo = mock(DockerImageRepository::class.java)
        val viewModel = MainViewModel(repo)
        
        val initialImages = viewModel.appState.value.images
        val imageToToggle = initialImages.first()
        val initialState = imageToToggle.isFavorite
        
        viewModel.toggleFavorite(imageToToggle.id)
        
        val newImages = viewModel.appState.value.images
        val toggledImage = newImages.find { it.id == imageToToggle.id }!!
        assertTrue(toggledImage.isFavorite != initialState)
    }
}
```

- [ ] **Step 2: Run test to verify it fails**

Run: `./gradlew testDebugUnitTest --tests "com.valdemar.whalewatcher.ui.MainViewModelTest"`
Expected: FAIL due to missing `appState` and `toggleFavorite`.

- [ ] **Step 3: Write minimal implementation**

Modify `MainViewModel.kt` (Add imports for kotlinx.coroutines.flow.update):
```kotlin
package com.valdemar.whalewatcher.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.valdemar.whalewatcher.data.repository.DockerImageRepository
import com.valdemar.whalewatcher.ui.models.DummyCategory
import com.valdemar.whalewatcher.ui.models.DummyCollection
import com.valdemar.whalewatcher.ui.models.DummyData
import com.valdemar.whalewatcher.ui.models.DummyDockerImage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AppState(
    val images: List<DummyDockerImage> = DummyData.allImages,
    val categories: List<DummyCategory> = DummyData.categories,
    val collections: List<DummyCollection> = emptyList()
)

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: DockerImageRepository
) : ViewModel() {

    private val _appState = MutableStateFlow(AppState())
    val appState: StateFlow<AppState> = _appState.asStateFlow()

    fun toggleFavorite(imageId: String) {
        _appState.update { state ->
            val updatedImages = state.images.map { img ->
                if (img.id == imageId) img.copy(isFavorite = !img.isFavorite) else img
            }
            state.copy(images = updatedImages)
        }
    }
}
```

- [ ] **Step 4: Run test to verify it passes**

Run: `./gradlew testDebugUnitTest --tests "com.valdemar.whalewatcher.ui.MainViewModelTest"`
Expected: BUILD SUCCESSFUL

- [ ] **Step 5: Commit**

```bash
git add app/src/main/kotlin/com/valdemar/whalewatcher/ui/MainViewModel.kt app/src/test/kotlin/com/valdemar/whalewatcher/ui/MainViewModelTest.kt
git commit -m "feat: setup appState in MainViewModel and toggleFavorite logic"
```

---

### Task 4: Fix Navigation State Configuration

**Files:**
- Modify: `app/src/main/kotlin/com/valdemar/whalewatcher/ui/navigation/BottomNavigationBar.kt`
- Modify: `app/src/main/kotlin/com/valdemar/whalewatcher/ui/navigation/Screen.kt`

- [ ] **Step 1: Write minimal implementation**

Modify `Screen.kt`:
```kotlin
package com.valdemar.whalewatcher.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, @StringRes val resourceId: Int? = null, val icon: ImageVector? = null) {
    object Home : Screen("home", android.R.string.untitled, Icons.Filled.Home)
    object Search : Screen("search", android.R.string.search_go, Icons.Filled.Search)
    object Collections : Screen("collections", android.R.string.copy, Icons.AutoMirrored.Filled.List)
    
    object CategoryDetails : Screen("category/{categoryId}") {
        fun createRoute(categoryId: String) = "category/${android.net.Uri.encode(categoryId)}"
    }
    object ImageDetails : Screen("image/{imageId}") {
        fun createRoute(imageId: String) = "image/${android.net.Uri.encode(imageId)}"
    }
}

val BottomNavScreens = listOf(Screen.Home, Screen.Search, Screen.Collections)
```

Modify `BottomNavigationBar.kt` inside the `onClick` block to correctly route to "collections" instead of "library" and ensure label text reflects route:
```kotlin
// ... inside NavigationBarItem loop ...
label = { Text(text = screen.route.replaceFirstChar { it.uppercase() }) },
onClick = {
    navController.navigate(screen.route) {
        popUpTo(navController.graph.startDestinationId) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}
```

- [ ] **Step 2: Commit**

```bash
git add app/src/main/kotlin/com/valdemar/whalewatcher/ui/navigation/Screen.kt app/src/main/kotlin/com/valdemar/whalewatcher/ui/navigation/BottomNavigationBar.kt
git commit -m "fix: resolve navigation state loss and rename Library to Collections"
```

---

### Task 5: Standardize ImageCard Composable

**Files:**
- Modify: `app/src/main/kotlin/com/valdemar/whalewatcher/ui/components/ImageCard.kt`

- [ ] **Step 1: Write minimal implementation**

Modify `ImageCard.kt`:
```kotlin
package com.valdemar.whalewatcher.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.valdemar.whalewatcher.ui.models.DummyDockerImage
import com.valdemar.whalewatcher.ui.theme.SuccessGreen
import com.valdemar.whalewatcher.ui.theme.WarningOrange
import com.valdemar.whalewatcher.ui.theme.ErrorRed

@Composable
fun ImageCard(
    image: DummyDockerImage,
    onClick: () -> Unit,
    onFavoriteToggle: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth().clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                val statusColor = if (image.lastUpdated.startsWith("2026")) SuccessGreen else if (image.lastUpdated.startsWith("2025")) WarningOrange else ErrorRed
                Surface(modifier = Modifier.size(12.dp), shape = androidx.compose.foundation.shape.CircleShape, color = statusColor) {}
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "${image.namespace}/${image.name}", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.weight(1f))
                IconButton(onClick = onFavoriteToggle) {
                    Icon(
                        imageVector = if (image.isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                        contentDescription = "Favorite",
                        tint = if (image.isFavorite) Color.Red else MaterialTheme.colorScheme.onSurface
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = image.description, maxLines = 2, overflow = TextOverflow.Ellipsis, style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(imageVector = Icons.Filled.Star, contentDescription = "Stars", modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = image.starCount.toString())
                Spacer(modifier = Modifier.width(16.dp))
                Text(text = "⬇️ ${image.pullCount}")
            }
        }
    }
}
```

- [ ] **Step 2: Commit**

```bash
git add app/src/main/kotlin/com/valdemar/whalewatcher/ui/components/ImageCard.kt
git commit -m "feat: standardize ImageCard with favorites and status colors"
```

---

### Task 6: HomeScreen Implementation

**Files:**
- Modify: `app/src/main/kotlin/com/valdemar/whalewatcher/ui/screens/HomeScreen.kt`

- [ ] **Step 1: Write minimal implementation**

Modify `HomeScreen.kt` to observe state and show horizontal rows and vertical grids:
```kotlin
package com.valdemar.whalewatcher.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.valdemar.whalewatcher.ui.MainViewModel
import com.valdemar.whalewatcher.ui.components.ImageCard

@Composable
fun HomeScreen(
    viewModel: MainViewModel,
    onNavigateToCategory: (String) -> Unit,
    onNavigateToImage: (String) -> Unit
) {
    val state by viewModel.appState.collectAsState()
    val favorites = state.images.filter { it.isFavorite }
    val popular = state.images.take(5) // Just taking first 5 as popular

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        if (favorites.isNotEmpty()) {
            Text("Your Favorites", style = MaterialTheme.typography.headlineSmall)
            LazyRow(contentPadding = PaddingValues(vertical = 8.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(favorites) { img ->
                    ImageCard(image = img, onClick = { onNavigateToImage(img.id) }, onFavoriteToggle = { viewModel.toggleFavorite(img.id) }, modifier = Modifier.width(280.dp))
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text("Popular", style = MaterialTheme.typography.headlineSmall)
        LazyRow(contentPadding = PaddingValues(vertical = 8.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            items(popular) { img ->
                ImageCard(image = img, onClick = { onNavigateToImage(img.id) }, onFavoriteToggle = { viewModel.toggleFavorite(img.id) }, modifier = Modifier.width(280.dp))
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text("Explore Categories", style = MaterialTheme.typography.headlineSmall)
        LazyVerticalGrid(columns = GridCells.Fixed(2), contentPadding = PaddingValues(vertical = 8.dp), horizontalArrangement = Arrangement.spacedBy(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(state.categories) { category ->
                Card(modifier = Modifier.clickable { onNavigateToCategory(category.id) }.height(100.dp)) {
                    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = category.emojiIcon, style = MaterialTheme.typography.headlineMedium)
                        Text(text = category.title)
                    }
                }
            }
        }
    }
}
```

- [ ] **Step 2: Commit**

```bash
git add app/src/main/kotlin/com/valdemar/whalewatcher/ui/screens/HomeScreen.kt
git commit -m "feat: implement HomeScreen layout with state observation"
```

---

### Task 7: Setup MainActivity Routing

**Files:**
- Modify: `app/src/main/kotlin/com/valdemar/whalewatcher/ui/MainActivity.kt`

- [ ] **Step 1: Write minimal implementation**

Modify `WhaleWatcherApp` inside `MainActivity.kt` to pass the `viewModel` and new routes:
```kotlin
// In WhaleWatcherApp composable:
import androidx.lifecycle.viewmodel.compose.viewModel
...
@Composable
fun WhaleWatcherApp(viewModel: MainViewModel = viewModel()) {
    // ... setup navController ...
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = Modifier.padding(innerPadding)
    ) {
        composable(Screen.Home.route) {
            HomeScreen(
                viewModel = viewModel,
                onNavigateToCategory = { id -> navController.navigate(Screen.CategoryDetails.createRoute(id)) },
                onNavigateToImage = { id -> navController.navigate(Screen.ImageDetails.createRoute(id)) }
            )
        }
        // ... hook up other basic screens similarly ...
    }
}
```

- [ ] **Step 2: Commit**

```bash
git add app/src/main/kotlin/com/valdemar/whalewatcher/ui/MainActivity.kt
git commit -m "feat: update MainActivity routing to use ViewModel and new navigation logic"
```
