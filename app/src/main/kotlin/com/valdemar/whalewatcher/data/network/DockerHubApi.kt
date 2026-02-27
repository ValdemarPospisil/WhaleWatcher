package com.valdemar.whalewatcher.data.network

import retrofit2.http.GET
import retrofit2.http.Path

interface DockerHubApi {

    @GET("v2/namespaces/{namespace}/repositories")
    suspend fun getPublicRepositories(
        @Path("namespace") namespace: String
    ): DockerRepositoryResponse
}
