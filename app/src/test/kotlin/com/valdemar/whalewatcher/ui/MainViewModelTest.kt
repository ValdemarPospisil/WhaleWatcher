package com.valdemar.whalewatcher.ui

import com.valdemar.whalewatcher.data.network.DockerRepository
import com.valdemar.whalewatcher.data.network.DockerRepositoryResponse
import com.valdemar.whalewatcher.data.repository.DockerImageRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModelTest {
    private val testDispatcher = UnconfinedTestDispatcher()
    private lateinit var repository: DockerImageRepository
    private lateinit var viewModel: MainViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        repository = mockk()
        viewModel = MainViewModel(repository)
    }

    @After
    fun teardown() {
        Dispatchers.resetMain()
    }

    @Test
    fun fetchImage_success_updatesStateToSuccess() =
        runTest {
            val mockResponse =
                DockerRepositoryResponse(
                    count = 1,
                    results = listOf(DockerRepository(name = "alpine", namespace = "library")),
                )
            coEvery { repository.getRepositories("library") } returns Result.success(mockResponse)

            viewModel.fetchImages()

            val state = viewModel.uiState.value
            assertTrue(state is MainUiState.Success)
            assertEquals("alpine", (state as MainUiState.Success).imageName)
        }

    @Test
    fun fetchImage_error_updatesStateToError() =
        runTest {
            coEvery { repository.getRepositories("library") } returns Result.failure(RuntimeException("Network error"))

            viewModel.fetchImages()

            val state = viewModel.uiState.value
            assertTrue(state is MainUiState.Error)
            assertEquals("Network error", (state as MainUiState.Error).message)
        }
}
