package com.valdemar.whalewatcher.ui

import com.valdemar.whalewatcher.data.network.DockerTag
import com.valdemar.whalewatcher.data.network.DockerTagsResponse
import com.valdemar.whalewatcher.data.network.RepositoryInfo
import com.valdemar.whalewatcher.data.repository.DockerImageRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ImageDetailViewModelTest {
    private val testDispatcher = StandardTestDispatcher()
    private lateinit var repository: DockerImageRepository
    private lateinit var viewModel: ImageDetailViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        repository = mockk()
        viewModel = ImageDetailViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `loadDetails success emits Success state with repository info and tags`() =
        runTest {
            val mockRepoInfo =
                RepositoryInfo(
                    name = "nginx",
                    namespace = "library",
                    description = "Official build of Nginx.",
                    starCount = 1000,
                    pullCount = 50000,
                )
            val mockTagsResponse =
                DockerTagsResponse(
                    count = 1,
                    results = listOf(DockerTag(name = "latest", fullSize = 1000000)),
                )

            coEvery { repository.getImageDetails("library", "nginx") } returns Result.success(mockRepoInfo)
            coEvery { repository.getImageTags("library", "nginx") } returns Result.success(mockTagsResponse)

            viewModel.loadDetails("library", "nginx")
            advanceUntilIdle()

            assertTrue(viewModel.uiState.value is ImageDetailUiState.Success)
            val successState = viewModel.uiState.value as ImageDetailUiState.Success

            assertEquals("nginx", successState.repositoryInfo.name)
            assertEquals(1, successState.tags.size)
            assertEquals("latest", successState.tags[0].name)
        }

    @Test
    fun `loadDetails failure on details API emits Error state`() =
        runTest {
            coEvery {
                repository.getImageDetails("library", "nginx")
            } returns Result.failure(RuntimeException("Network error"))

            // Tags shouldn't matter if details fail, but let's mock it just in case it runs in parallel
            val mockTagsResponse = DockerTagsResponse(count = 0, results = emptyList())
            coEvery { repository.getImageTags("library", "nginx") } returns Result.success(mockTagsResponse)

            viewModel.loadDetails("library", "nginx")
            advanceUntilIdle()

            assertTrue(viewModel.uiState.value is ImageDetailUiState.Error)
            val errorState = viewModel.uiState.value as ImageDetailUiState.Error
            assertEquals("Network error", errorState.message)
        }

    @Test
    fun `loadDetails failure on tags API emits Error state`() =
        runTest {
            val mockRepoInfo = RepositoryInfo(name = "nginx", namespace = "library")
            coEvery { repository.getImageDetails("library", "nginx") } returns Result.success(mockRepoInfo)
            coEvery {
                repository.getImageTags("library", "nginx")
            } returns Result.failure(RuntimeException("Tags error"))

            viewModel.loadDetails("library", "nginx")
            advanceUntilIdle()

            assertTrue(viewModel.uiState.value is ImageDetailUiState.Error)
            val errorState = viewModel.uiState.value as ImageDetailUiState.Error
            assertEquals("Tags error", errorState.message)
        }
}
