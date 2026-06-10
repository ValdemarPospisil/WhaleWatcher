package com.valdemar.whalewatcher.ui

import com.valdemar.whalewatcher.data.local.entities.CollectionEntity
import com.valdemar.whalewatcher.data.local.entities.CollectionWithImages
import com.valdemar.whalewatcher.data.repository.DockerImageRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var repository: DockerImageRepository
    private lateinit var prepopulator: com.valdemar.whalewatcher.data.local.DatabasePrepopulator
    private lateinit var viewModel: HomeViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        repository = mockk()
        prepopulator = mockk(relaxed = true)

        val mockCollections = listOf(
            CollectionWithImages(CollectionEntity(1, "Favorites", "Fav", "Heart", isSystem = true, isFavorite = true), emptyList()),
            CollectionWithImages(CollectionEntity(2, "Databases", "Desc", "Storage", isSystem = true, isFavorite = false), emptyList()),
            CollectionWithImages(CollectionEntity(3, "My List", "Desc", "List", isSystem = false, isFavorite = false), emptyList())
        )
        
        coEvery { repository.getAllCollectionsWithImages() } returns flowOf(mockCollections)
        coEvery { repository.ensureFavoritesCollectionExists() } returns Unit

        viewModel = HomeViewModel(repository, prepopulator)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `viewModel filters system categories`() = runTest {
        advanceUntilIdle()

        val state = viewModel.systemCategoriesState.value
        assertEquals(1, state.size)
        assertEquals("Databases", state[0].collection.name)
    }
}
