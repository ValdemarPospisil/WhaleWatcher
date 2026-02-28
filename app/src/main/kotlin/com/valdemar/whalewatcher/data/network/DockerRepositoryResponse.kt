package com.valdemar.whalewatcher.data.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DockerRepositoryResponse(
    val count: Int,
    val next: String? = null,
    val previous: String? = null,
    val results: List<DockerRepository>
)

@Serializable
data class DockerRepository(
    val name: String,
    val namespace: String,
    @SerialName("repository_type") val repositoryType: String? = null,
    val status: Int? = null,
    val description: String? = null,
    @SerialName("is_private") val isPrivate: Boolean? = null,
    @SerialName("star_count") val starCount: Long? = null,
    @SerialName("pull_count") val pullCount: Long? = null,
    @SerialName("last_updated") val lastUpdated: String? = null
)
