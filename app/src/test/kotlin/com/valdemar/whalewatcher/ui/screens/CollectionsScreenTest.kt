package com.valdemar.whalewatcher.ui.screens

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.valdemar.whalewatcher.data.local.entities.CollectionEntity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [33])
class CollectionsScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun `collections are displayed`() {
        val collections = listOf(
            CollectionEntity(
                id = 1,
                name = "Favorites",
                description = "My Favs",
                iconName = "Heart",
                isSystem = true,
                isFavorite = true
            )
        )
        
        composeTestRule.setContent {
            CollectionsScreen(
                collections = collections,
                onCreateCollection = {_, _, _ -> },
                onCollectionClick = {}
            )
        }

        composeTestRule.onNodeWithText("Favorites").assertIsDisplayed()
        composeTestRule.onNodeWithText("My Favs").assertIsDisplayed()
    }

    @Test
    fun `clicking add opens create dialog`() {
        composeTestRule.setContent {
            CollectionsScreen(
                collections = emptyList(),
                onCreateCollection = {_, _, _ -> },
                onCollectionClick = {}
            )
        }

        composeTestRule.onNodeWithContentDescription("Create Collection").performClick()
        
        composeTestRule.onNodeWithText("Create new collection").assertIsDisplayed()
    }
}
