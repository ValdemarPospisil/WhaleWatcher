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
}
