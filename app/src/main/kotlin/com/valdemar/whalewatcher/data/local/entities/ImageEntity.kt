package com.valdemar.whalewatcher.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "images")
data class ImageEntity(
    @PrimaryKey val id: String,
    val name: String,
    val namespace: String,
)
