package com.valdemar.whalewatcher.data.local.entities

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class CollectionWithImages(
    @Embedded val collection: CollectionEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            value = CollectionImageCrossRef::class,
            parentColumn = "collectionId",
            entityColumn = "imageId"
        )
    )
    val images: List<DockerImageEntity>
)
