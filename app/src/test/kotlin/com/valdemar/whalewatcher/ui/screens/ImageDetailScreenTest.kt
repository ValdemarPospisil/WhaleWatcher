package com.valdemar.whalewatcher.ui.screens

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.valdemar.whalewatcher.data.network.RepositoryInfo
import com.valdemar.whalewatcher.ui.ImageDetailUiState
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [33])
class ImageDetailScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun `clicking favorite icon triggers callback`() {
        var favoriteClicked = false
        val mockRepoInfo = RepositoryInfo(
            name = "nginx",
            namespace = "library",
            description = "desc"
        )
        
        composeTestRule.setContent {
            ImageDetailScreen(
                uiState = ImageDetailUiState.Success(mockRepoInfo, emptyList()),
                namespace = "library",
                repository = "nginx",
                onNavigateBack = {},
                onToggleFavorite = { favoriteClicked = true },
                onAddToCollection = {}
            )
        }

        composeTestRule.onNodeWithContentDescription("Favorite").performClick()
        
        assert(favoriteClicked)
    }

    @Test
    fun `clicking save shows bottom sheet`() {
        var bottomSheetShown = false
        val mockRepoInfo = RepositoryInfo(
            name = "nginx",
            namespace = "library",
            description = "desc"
        )
        
        composeTestRule.setContent {
            ImageDetailScreen(
                uiState = ImageDetailUiState.Success(mockRepoInfo, emptyList()),
                namespace = "library",
                repository = "nginx",
                onNavigateBack = {},
                onToggleFavorite = {},
                onAddToCollection = { bottomSheetShown = true }
            )
        }

        composeTestRule.onNodeWithText("💾").performClick()
        
        assert(bottomSheetShown)
    }
}
