package com.valdemar.whalewatcher.data.network

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DockerHubApi {

    @GET("v2/namespaces/{namespace}/repositories")
    suspend fun getPublicRepositories(
        @Path("namespace") namespace: String
    ): DockerRepositoryResponse

    @GET("v2/search/repositories/")
    suspend fun searchRepositories(
        @Query("query") query: String,
        @Query("page") page: Int? = null
    ): DockerSearchResponse
}
