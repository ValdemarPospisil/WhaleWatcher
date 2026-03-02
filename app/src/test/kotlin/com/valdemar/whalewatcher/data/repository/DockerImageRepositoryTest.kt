package com.valdemar.whalewatcher.data.repository

import com.valdemar.whalewatcher.data.network.DockerHubApi
import com.valdemar.whalewatcher.data.network.DockerRepository
import com.valdemar.whalewatcher.data.network.DockerRepositoryResponse
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
}
