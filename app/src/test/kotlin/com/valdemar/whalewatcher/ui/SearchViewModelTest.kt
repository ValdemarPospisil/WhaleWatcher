package com.valdemar.whalewatcher.ui

import com.valdemar.whalewatcher.data.network.DockerSearchResponse
import com.valdemar.whalewatcher.data.network.DockerSearchResult
import com.valdemar.whalewatcher.data.repository.DockerImageRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlinx.coroutines.yield
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SearchViewModelTest {
    private val testDispatcher = StandardTestDispatcher()
    private lateinit var repository: DockerImageRepository
    private lateinit var viewModel: SearchViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        repository = mockk()
        viewModel = SearchViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `search input debounces by 500ms before making api call`() =
        runTest {
            val mockResponse =
                DockerSearchResponse(
                    count = 1,
                    results = listOf(DockerSearchResult(repoName = "test-image")),
                )
            coEvery { repository.searchImages("test", 1) } returns Result.success(mockResponse)

            // Give init block time to process initial empty string emission
            advanceTimeBy(501)
            yield()

            viewModel.onSearchQueryChanged("t")
            viewModel.onSearchQueryChanged("te")
            viewModel.onSearchQueryChanged("tes")
            viewModel.onSearchQueryChanged("test")

            // Before 500ms, state should be Idle (or initial)
            advanceTimeBy(400)
            yield()
            assertTrue(viewModel.uiState.value is SearchUiState.Idle)

            // After 500ms, it should fetch
            advanceTimeBy(101)
            yield() // Allow flow to emit

            assertTrue(viewModel.uiState.value is SearchUiState.Success)
            val successState = viewModel.uiState.value as SearchUiState.Success
            assertEquals(1, successState.totalCount)
            assertEquals(1, successState.results.size)
            assertEquals("test-image", successState.results[0].repoName)
        }

    @Test
    fun `fetching next page appends results and updates state`() =
        runTest {
            val mockResponse1 =
                DockerSearchResponse(
                    count = 2,
                    next = "page=2",
                    results = listOf(DockerSearchResult(repoName = "test-image-1")),
                )
            val mockResponse2 =
                DockerSearchResponse(
                    count = 2,
                    next = null,
                    results = listOf(DockerSearchResult(repoName = "test-image-2")),
                )
            coEvery { repository.searchImages("test", 1) } returns Result.success(mockResponse1)
            coEvery { repository.searchImages("test", 2) } returns Result.success(mockResponse2)

            viewModel.onSearchQueryChanged("test")
            advanceTimeBy(501)
            yield()

            var successState = viewModel.uiState.value as SearchUiState.Success
            assertEquals(1, successState.results.size)
            assertEquals("test-image-1", successState.results[0].repoName)

            viewModel.loadNextPage()
            yield()

            successState = viewModel.uiState.value as SearchUiState.Success
            assertEquals(2, successState.results.size)
            assertEquals("test-image-1", successState.results[0].repoName)
            assertEquals("test-image-2", successState.results[1].repoName)
        }

    @Test
    fun `empty state is emitted when no results found`() =
        runTest {
            val mockResponse = DockerSearchResponse(count = 0, results = emptyList())
            coEvery { repository.searchImages("empty", 1) } returns Result.success(mockResponse)

            viewModel.onSearchQueryChanged("empty")
            advanceTimeBy(501)
            yield()

            assertTrue(viewModel.uiState.value is SearchUiState.Empty)
        }

    @Test
    fun `error state is emitted on failure`() =
        runTest {
            coEvery { repository.searchImages("error", 1) } returns Result.failure(RuntimeException("Network error"))

            viewModel.onSearchQueryChanged("error")
            advanceTimeBy(501)
            yield()

            assertTrue(viewModel.uiState.value is SearchUiState.Error)
            val errorState = viewModel.uiState.value as SearchUiState.Error
            assertEquals("Network error", errorState.message)
        }
}
