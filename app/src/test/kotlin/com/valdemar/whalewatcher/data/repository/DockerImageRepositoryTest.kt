package com.valdemar.whalewatcher.data.repository

import com.valdemar.whalewatcher.data.network.DockerHubApi
import com.valdemar.whalewatcher.data.network.DockerRepository
import com.valdemar.whalewatcher.data.network.DockerRepositoryResponse
import com.valdemar.whalewatcher.data.network.DockerSearchResponse
import com.valdemar.whalewatcher.data.network.DockerSearchResult
import com.valdemar.whalewatcher.data.network.DockerTag
import com.valdemar.whalewatcher.data.network.DockerTagsResponse
import com.valdemar.whalewatcher.data.network.RepositoryInfo
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class DockerImageRepositoryTest {

    private lateinit var api: DockerHubApi
    private lateinit var repository: DockerImageRepository

    @Before
    fun setup() {
        api = mockk()
        repository = DockerImageRepository(api)
    }

    @Test
    fun getPublicRepositories_success_returnsResultSuccess() = runBlocking {
        val mockResponse = DockerRepositoryResponse(
            count = 1,
            results = listOf(
                DockerRepository(name = "ubuntu", namespace = "library")
            )
        )
        coEvery { api.getPublicRepositories("library") } returns mockResponse

        val result = repository.getRepositories("library")

        assertTrue(result.isSuccess)
        assertEquals(1, result.getOrNull()?.count)
        assertEquals("ubuntu", result.getOrNull()?.results?.first()?.name)
    }

    @Test
    fun getPublicRepositories_error_returnsResultFailure() = runBlocking {
        val exception = RuntimeException("Network Error")
        coEvery { api.getPublicRepositories("library") } throws exception

        val result = repository.getRepositories("library")

        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
    }

    @Test
    fun searchImages_success_returnsResultSuccess() = runBlocking {
        val mockResponse = DockerSearchResponse(
            count = 1,
            results = listOf(
                DockerSearchResult(repoName = "ubuntu")
            )
        )
        coEvery { api.searchRepositories("ubuntu", 1) } returns mockResponse

        val result = repository.searchImages("ubuntu", 1)

        assertTrue(result.isSuccess)
        assertEquals(1, result.getOrNull()?.count)
        assertEquals("ubuntu", result.getOrNull()?.results?.first()?.repoName)
    }

    @Test
    fun searchImages_error_returnsResultFailure() = runBlocking {
        val exception = RuntimeException("Network Error")
        coEvery { api.searchRepositories("ubuntu", 1) } throws exception

        val result = repository.searchImages("ubuntu", 1)

        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
    }

    @Test
    fun getImageDetails_success_returnsResultSuccess() = runBlocking {
        val mockResponse = RepositoryInfo(
            name = "ubuntu",
            namespace = "library"
        )
        coEvery { api.getRepository("library", "ubuntu") } returns mockResponse

        val result = repository.getImageDetails("library", "ubuntu")

        assertTrue(result.isSuccess)
        assertEquals("ubuntu", result.getOrNull()?.name)
        assertEquals("library", result.getOrNull()?.namespace)
    }

    @Test
    fun getImageDetails_error_returnsResultFailure() = runBlocking {
        val exception = RuntimeException("Network Error")
        coEvery { api.getRepository("library", "ubuntu") } throws exception

        val result = repository.getImageDetails("library", "ubuntu")

        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
    }

    @Test
    fun getImageTags_success_returnsResultSuccess() = runBlocking {
        val mockResponse = DockerTagsResponse(
            count = 1,
            results = listOf(
                DockerTag(name = "latest")
            )
        )
        coEvery { api.getRepositoryTags("library", "ubuntu", 1, 50) } returns mockResponse

        val result = repository.getImageTags("library", "ubuntu", 1, 50)

        assertTrue(result.isSuccess)
        assertEquals(1, result.getOrNull()?.count)
        assertEquals("latest", result.getOrNull()?.results?.first()?.name)
    }

    @Test
    fun getImageTags_error_returnsResultFailure() = runBlocking {
        val exception = RuntimeException("Network Error")
        coEvery { api.getRepositoryTags("library", "ubuntu", 1, 50) } throws exception

        val result = repository.getImageTags("library", "ubuntu", 1, 50)

        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
    }
}