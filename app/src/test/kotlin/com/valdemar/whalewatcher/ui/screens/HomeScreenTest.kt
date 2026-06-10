package com.valdemar.whalewatcher.ui.screens

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.valdemar.whalewatcher.data.local.entities.CollectionEntity
import com.valdemar.whalewatcher.data.local.entities.CollectionWithImages
import com.valdemar.whalewatcher.ui.HomeViewModel
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(instrumentedPackages = ["androidx.loader.content"])
class HomeScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun `HomeScreen displays categories and handles clicks`() {
        val viewModel = mockk<HomeViewModel>(relaxed = true)
        
        val systemCategory = CollectionWithImages(
            collection = CollectionEntity(1, "Databases", "Desc", "Storage", isSystem = true, isFavorite = false),
            images = emptyList()
        )
        
        val collectionsState = MutableStateFlow(listOf(systemCategory))
        val systemCategoriesState = MutableStateFlow(listOf(systemCategory))
        
        every { viewModel.collectionsState } returns collectionsState
        every { viewModel.systemCategoriesState } returns systemCategoriesState
        
        var clickedCategoryId: Long? = null
        
        composeTestRule.setContent {
            HomeScreen(
                onNavigateToList = { clickedCategoryId = it },
                onNavigateToImage = { _, _ -> },
                viewModel = viewModel
            )
        }
        
        composeTestRule.onNodeWithText("Categories").assertExists()
        composeTestRule.onNodeWithText("Databases").assertExists()
        
        composeTestRule.onNodeWithText("Databases").performClick()
        
        assertEquals(1L, clickedCategoryId)
    }
}
