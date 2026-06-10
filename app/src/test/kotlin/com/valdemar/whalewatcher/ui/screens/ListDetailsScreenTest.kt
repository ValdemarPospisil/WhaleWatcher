package com.valdemar.whalewatcher.ui.screens

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.valdemar.whalewatcher.data.local.entities.CollectionEntity
import com.valdemar.whalewatcher.data.local.entities.CollectionWithImages
import com.valdemar.whalewatcher.ui.ListDetailsViewModel
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(instrumentedPackages = ["androidx.loader.content"])
class ListDetailsScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun `ListDetailsScreen shows Read-Only badge and description for system categories`() {
        val viewModel = mockk<ListDetailsViewModel>(relaxed = true)

        val systemCategory = CollectionWithImages(
            collection = CollectionEntity(1, "Databases", "Essential relational and NoSQL databases.", "Storage", isSystem = true, isFavorite = false),
            images = emptyList()
        )

        val collectionState = MutableStateFlow<CollectionWithImages?>(systemCategory)
        every { viewModel.collectionState } returns collectionState

        composeTestRule.setContent {
            ListDetailsScreen(
                listId = 1L,
                onNavigateBack = {},
                onNavigateToImage = { _, _ -> },
                viewModel = viewModel
            )
        }

        composeTestRule.onNodeWithText("Read-Only").assertExists()
        composeTestRule.onNodeWithText("Essential relational and NoSQL databases.").assertExists()
    }
}
