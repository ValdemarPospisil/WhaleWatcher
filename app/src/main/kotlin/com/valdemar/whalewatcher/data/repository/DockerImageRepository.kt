package com.valdemar.whalewatcher.data.repository

import com.valdemar.whalewatcher.data.network.DockerHubApi
import com.valdemar.whalewatcher.data.network.DockerRepositoryResponse
import com.valdemar.whalewatcher.data.network.DockerSearchResponse
import com.valdemar.whalewatcher.data.network.DockerTagsResponse
import com.valdemar.whalewatcher.data.network.RepositoryInfo
import javax.inject.Inject

class DockerImageRepository
    @Inject
    constructor(
        private val api: DockerHubApi,
    ) {
        suspend fun getRepositories(namespace: String): Result<DockerRepositoryResponse> {
            return try {
                val response = api.getPublicRepositories(namespace)
                Result.success(response)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }

        suspend fun searchImages(
            query: String,
            page: Int? = null,
        ): Result<DockerSearchResponse> {
            return try {
                val response = api.searchRepositories(query, page)
                Result.success(response)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }

        suspend fun getImageDetails(
            namespace: String,
            repository: String,
        ): Result<RepositoryInfo> {
            return try {
                val response = api.getRepository(namespace, repository)
                Result.success(response)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }

        suspend fun getImageTags(
            namespace: String,
            repository: String,
            page: Int? = null,
            pageSize: Int? = null,
        ): Result<DockerTagsResponse> {
            return try {
                val response = api.getRepositoryTags(namespace, repository, page, pageSize)
                Result.success(response)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
