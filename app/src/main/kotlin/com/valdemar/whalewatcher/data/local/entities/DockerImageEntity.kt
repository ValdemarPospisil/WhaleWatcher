package com.valdemar.whalewatcher.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "docker_images")
data class DockerImageEntity(
    // e.g. "library/nginx" or just "nginx" depending on API
    @PrimaryKey
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "namespace")
    val namespace: String,
    @ColumnInfo(name = "description")
    val description: String,
    @ColumnInfo(name = "stars")
    val stars: Int,
    @ColumnInfo(name = "pull_count")
    val pullCount: String,
    @ColumnInfo(name = "is_favorite")
    val isFavorite: Boolean = false,
    @ColumnInfo(name = "last_updated")
    val lastUpdated: Long = System.currentTimeMillis(),
)
