package com.valdemar.whalewatcher.ui

import com.valdemar.whalewatcher.data.local.entities.CollectionEntity
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
class CollectionsViewModelTest {
    private val testDispatcher = StandardTestDispatcher()
    private lateinit var repository: DockerImageRepository
    private lateinit var viewModel: CollectionsViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        repository = mockk()
        
        val mockCollections = listOf(
            CollectionEntity(1, "Favorites", "Fav", "Heart", true, true),
            CollectionEntity(2, "My List", "Desc", "List", false, false)
        )
        coEvery { repository.getAllCollections() } returns flowOf(mockCollections)
        coEvery { repository.ensureFavoritesCollectionExists() } returns Unit
        
        viewModel = CollectionsViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `viewModel collects collections on init`() = runTest {
        advanceUntilIdle()
        
        val state = viewModel.collectionsState.value
        assertEquals(2, state.size)
        assertEquals("Favorites", state[0].name)
    }
}
