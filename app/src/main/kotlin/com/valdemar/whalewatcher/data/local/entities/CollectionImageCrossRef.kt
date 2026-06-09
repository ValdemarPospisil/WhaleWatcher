package com.valdemar.whalewatcher.data.local.entities

import androidx.room.Entity
import androidx.room.Index

@Entity(
    tableName = "collection_image_cross_ref",
    primaryKeys = ["collectionId", "imageName"],
    indices = [Index(value = ["imageName"])]
)
data class CollectionImageCrossRef(
    val collectionId: Long,
    val imageName: String
)
