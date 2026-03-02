package com.valdemar.whalewatcher.data.network

import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType

class DockerHubApiTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var api: DockerHubApi
    private val json = Json { ignoreUnknownKeys = true }

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start()

        val contentType = "application/json".toMediaType()
        val retrofit = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()

        api = retrofit.create(DockerHubApi::class.java)
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
    }

    @Test
    fun getPublicRepositories_returnsParsedResponse() = runBlocking {
        val mockResponseJson = """
        {
          "count": 1,
          "next": null,
          "previous": null,
          "results": [
            {
              "name": "ubuntu",
              "namespace": "library",
              "pull_count": 123456
            }
          ]
        }
        """.trimIndent()

        mockWebServer.enqueue(MockResponse().setBody(mockResponseJson).setResponseCode(200))

        val response = api.getPublicRepositories("library")
        
        val recordedRequest = mockWebServer.takeRequest()
        assertEquals("/v2/namespaces/library/repositories", recordedRequest.path)
        assertEquals("GET", recordedRequest.method)
        
        assertEquals(1, response.count)
        assertEquals("ubuntu", response.results[0].name)
    }

    @Test
    fun searchRepositories_returnsParsedResponse() = runBlocking {
        val mockResponseJson = """
        {
          "count": 5029,
          "next": "https://hub.docker.com/v2/search/repositories/?page=2&query=minecraft",
          "previous": null,
          "results": [
            {
              "repo_name": "pufferpanel/minecraft",
              "short_description": "Minecraft image for pufferd",
              "star_count": 8,
              "pull_count": 255271,
              "repo_owner": "",
              "is_automated": true,
              "is_official": false
            }
          ]
        }
        """.trimIndent()

        mockWebServer.enqueue(MockResponse().setBody(mockResponseJson).setResponseCode(200))

        val response = api.searchRepositories("minecraft", 1)

        val recordedRequest = mockWebServer.takeRequest()
        assertEquals("/v2/search/repositories/?query=minecraft&page=1", recordedRequest.path)
        assertEquals("GET", recordedRequest.method)

        assertEquals(5029, response.count)
        assertEquals("https://hub.docker.com/v2/search/repositories/?page=2&query=minecraft", response.next)
        assertEquals(1, response.results.size)
        assertEquals("pufferpanel/minecraft", response.results[0].repoName)
        assertEquals("Minecraft image for pufferd", response.results[0].shortDescription)
        assertEquals(8L, response.results[0].starCount)
        assertEquals(255271L, response.results[0].pullCount)
    }

    @Test
    fun getRepository_returnsParsedResponse() = runBlocking {
        val mockResponseJson = """
        {
          "name": "minecraft",
          "namespace": "pufferpanel",
          "repository_type": "image",
          "status": 1,
          "status_description": "active",
          "description": "Minecraft image for pufferd",
          "is_private": false,
          "star_count": 8,
          "pull_count": 255271,
          "last_updated": "2023-01-01T12:00:00.000Z",
          "date_registered": "2020-01-01T12:00:00.000Z"
        }
        """.trimIndent()

        mockWebServer.enqueue(MockResponse().setBody(mockResponseJson).setResponseCode(200))

        val response = api.getRepository("pufferpanel", "minecraft")

        val recordedRequest = mockWebServer.takeRequest()
        assertEquals("/v2/namespaces/pufferpanel/repositories/minecraft", recordedRequest.path)
        assertEquals("GET", recordedRequest.method)

        assertEquals("minecraft", response.name)
        assertEquals("pufferpanel", response.namespace)
        assertEquals("Minecraft image for pufferd", response.description)
        assertEquals(8L, response.starCount)
        assertEquals(255271L, response.pullCount)
        assertEquals("2023-01-01T12:00:00.000Z", response.lastUpdated)
        assertEquals(false, response.isPrivate)
    }

    @Test
    fun getRepositoryTags_returnsParsedResponse() = runBlocking {
        val mockResponseJson = """
        {
          "count": 2,
          "next": null,
          "previous": null,
          "results": [
            {
              "name": "latest",
              "full_size": 1024000,
              "tag_last_pushed": "2023-01-01T12:00:00.000Z"
            }
          ]
        }
        """.trimIndent()

        mockWebServer.enqueue(MockResponse().setBody(mockResponseJson).setResponseCode(200))

        val response = api.getRepositoryTags("pufferpanel", "minecraft")

        val recordedRequest = mockWebServer.takeRequest()
        assertEquals("/v2/namespaces/pufferpanel/repositories/minecraft/tags", recordedRequest.path)
        assertEquals("GET", recordedRequest.method)

        assertEquals(2, response.count)
        assertEquals(1, response.results.size)
        assertEquals("latest", response.results[0].name)
        assertEquals(1024000L, response.results[0].fullSize)
        assertEquals("2023-01-01T12:00:00.000Z", response.results[0].tagLastPushed)
    }
}
