package com.valdemar.whalewatcher.data.local

import android.content.Context
import com.valdemar.whalewatcher.data.local.dao.CollectionDao
import com.valdemar.whalewatcher.data.local.dao.ImageDao
import com.valdemar.whalewatcher.data.local.entities.CollectionEntity
import com.valdemar.whalewatcher.data.local.entities.CollectionImageCrossRef
import com.valdemar.whalewatcher.data.local.entities.DockerImageEntity
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import javax.inject.Inject

@Serializable
data class CategoryJson(
    val name: String,
    val description: String,
    val iconName: String,
    val isSystem: Boolean,
    val images: List<ImageJson>
)

@Serializable
data class ImageJson(
    val namespace: String,
    val name: String,
    val description: String,
    val pullCount: String,
    val stars: Int
)

class DatabasePrepopulator @Inject constructor(
    @ApplicationContext private val context: Context,
    private val collectionDao: CollectionDao,
    private val imageDao: ImageDao
) {
    suspend fun prepopulate() {
        val jsonString = context.assets.open("system_categories.json").bufferedReader().use { it.readText() }
        val categories = Json.decodeFromString<List<CategoryJson>>(jsonString)
        
        for (category in categories) {
            val collectionEntity = CollectionEntity(
                name = category.name,
                description = category.description,
                iconName = category.iconName,
                isSystem = category.isSystem,
                isFavorite = false
            )
            val collectionId = collectionDao.insertCollection(collectionEntity)

            for (img in category.images) {
                val imageEntity = DockerImageEntity(
                    namespace = img.namespace,
                    name = img.name,
                    description = img.description,
                    pullCount = img.pullCount,
                    stars = img.stars,
                    isFavorite = false
                )
                imageDao.insert(imageEntity)
                
                collectionDao.insertCollectionImageCrossRef(
                    CollectionImageCrossRef(
                        collectionId = collectionId,
                        imageName = img.name
                    )
                )
            }
        }
    }

    suspend fun prepopulateIfNeeded() {
        // Only run if we don't have any system categories yet.
        val collections = collectionDao.getAllCollections().first()
        val hasSystemCategories = collections.any { it.isSystem && !it.isFavorite }
        if (!hasSystemCategories) {
            prepopulate()
        }
    }
}
