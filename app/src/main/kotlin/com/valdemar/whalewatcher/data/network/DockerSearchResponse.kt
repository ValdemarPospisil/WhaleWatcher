package com.valdemar.whalewatcher.data.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DockerSearchResponse(
    val count: Int,
    val next: String? = null,
    val previous: String? = null,
    val results: List<DockerSearchResult>,
)

@Serializable
data class DockerSearchResult(
    @SerialName("repo_name") val repoName: String,
    @SerialName("short_description") val shortDescription: String? = null,
    @SerialName("star_count") val starCount: Long? = null,
    @SerialName("pull_count") val pullCount: Long? = null,
    @SerialName("repo_owner") val repoOwner: String? = null,
    @SerialName("is_automated") val isAutomated: Boolean? = null,
    @SerialName("is_official") val isOfficial: Boolean? = null,
)
