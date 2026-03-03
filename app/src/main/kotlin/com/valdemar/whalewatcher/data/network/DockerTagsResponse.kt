package com.valdemar.whalewatcher.data.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DockerTagsResponse(
    @SerialName("count") val count: Int,
    @SerialName("next") val next: String? = null,
    @SerialName("previous") val previous: String? = null,
    @SerialName("results") val results: List<DockerTag>,
)

@Serializable
data class DockerTag(
    @SerialName("name") val name: String,
    @SerialName("full_size") val fullSize: Long = 0,
    @SerialName("tag_last_pushed") val tagLastPushed: String? = null,
    @SerialName("last_updated") val lastUpdated: String? = null,
    @SerialName("status") val status: String? = null,
)
