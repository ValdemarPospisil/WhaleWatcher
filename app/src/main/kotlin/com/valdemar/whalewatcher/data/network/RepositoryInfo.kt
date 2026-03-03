package com.valdemar.whalewatcher.data.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RepositoryInfo(
    @SerialName("name") val name: String,
    @SerialName("namespace") val namespace: String,
    @SerialName("description") val description: String? = null,
    @SerialName("full_description") val fullDescription: String? = null,
    @SerialName("star_count") val starCount: Long = 0,
    @SerialName("pull_count") val pullCount: Long = 0,
    @SerialName("last_updated") val lastUpdated: String? = null,
    @SerialName("date_registered") val dateRegistered: String? = null,
    @SerialName("is_private") val isPrivate: Boolean = false,
    @SerialName("status") val status: Int? = null,
    @SerialName("status_description") val statusDescription: String? = null,
)
