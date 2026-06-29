package com.valdemar.whalewatcher.data.local.entities

import androidx.room.Entity
import androidx.room.Index

@Entity(
    tableName = "collection_image_cross_ref",
    primaryKeys = ["collectionId", "imageId"],
    indices = [Index(value = ["imageId"])]
)
data class CollectionImageCrossRef(
    val collectionId: Long,
    val imageId: String
)
