package com.valdemar.whalewatcher.data.network

import kotlinx.serialization.json.Json
import org.junit.Assert.assertEquals
import org.junit.Test

class DockerRepositoryResponseTest {
    private val json = Json { ignoreUnknownKeys = true }

    @Test
    fun parseRepositoryResponse() {
        val sampleJson =
            """
            {
              "count": 287,
              "next": "https://hub.docker.com/v2/namespaces/docker/repositories?page=2&page_size=2",
              "previous": null,
              "results": [
                {
                  "name": "highland_builder",
                  "namespace": "docker",
                  "repository_type": "image",
                  "status": 1,
                  "description": "Image for performing Docker build requests",
                  "is_private": false,
                  "star_count": 7,
                  "pull_count": 15722123,
                  "last_updated": "2023-06-20T10:44:45.459826Z"
                }
              ]
            }
            """.trimIndent()

        val response = json.decodeFromString<DockerRepositoryResponse>(sampleJson)

        assertEquals(287, response.count)
        assertEquals(1, response.results.size)

        val firstResult = response.results[0]
        assertEquals("highland_builder", firstResult.name)
        assertEquals("docker", firstResult.namespace)
        assertEquals(15722123L, firstResult.pullCount)
    }
}
