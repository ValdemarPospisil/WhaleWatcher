package com.valdemar.whalewatcher.data.network

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DockerHubApi {
    @GET("v2/namespaces/{namespace}/repositories")
    suspend fun getPublicRepositories(
        @Path("namespace") namespace: String,
    ): DockerRepositoryResponse

    @GET("v2/search/repositories/")
    suspend fun searchRepositories(
        @Query("query") query: String,
        @Query("page") page: Int? = null,
    ): DockerSearchResponse

    @GET("v2/namespaces/{namespace}/repositories/{repository}")
    suspend fun getRepository(
        @Path("namespace") namespace: String,
        @Path("repository") repository: String,
    ): RepositoryInfo

    @GET("v2/namespaces/{namespace}/repositories/{repository}/tags")
    suspend fun getRepositoryTags(
        @Path("namespace") namespace: String,
        @Path("repository") repository: String,
        @Query("page") page: Int? = null,
        @Query("page_size") pageSize: Int? = null,
    ): DockerTagsResponse
}
