package com.valdemar.whalewatcher.data.repository

import com.valdemar.whalewatcher.data.network.DockerHubApi
import com.valdemar.whalewatcher.data.network.DockerRepositoryResponse
import com.valdemar.whalewatcher.data.network.DockerSearchResponse
import javax.inject.Inject

class DockerImageRepository @Inject constructor(
    private val api: DockerHubApi
) {
    suspend fun getRepositories(namespace: String): Result<DockerRepositoryResponse> {
        return try {
            val response = api.getPublicRepositories(namespace)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun searchImages(query: String, page: Int? = null): Result<DockerSearchResponse> {
        return try {
            val response = api.searchRepositories(query, page)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
