package com.valdemar.whalewatcher.data.repository

import com.valdemar.whalewatcher.data.local.dao.CollectionDao
import com.valdemar.whalewatcher.data.local.dao.ImageDao
import com.valdemar.whalewatcher.data.local.entities.CollectionEntity
import com.valdemar.whalewatcher.data.local.entities.CollectionImageCrossRef
import com.valdemar.whalewatcher.data.local.entities.CollectionWithImages
import com.valdemar.whalewatcher.data.local.entities.DockerImageEntity
import com.valdemar.whalewatcher.data.network.DockerHubApi
import com.valdemar.whalewatcher.data.network.DockerRepositoryResponse
import com.valdemar.whalewatcher.data.network.DockerSearchResponse
import com.valdemar.whalewatcher.data.network.DockerTagsResponse
import com.valdemar.whalewatcher.data.network.RepositoryInfo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DockerImageRepository @Inject constructor(
    private val api: DockerHubApi,
    private val imageDao: ImageDao,
    private val collectionDao: CollectionDao
) {
    suspend fun getRepositories(namespace: String): Result<DockerRepositoryResponse> {
        return try {
            val response = api.getPublicRepositories(namespace)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun searchImages(query: String, page: Int? = null): Result<DockerSearchResponse> {
        return try {
            val response = api.searchRepositories(query, page)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getImageDetails(namespace: String, repository: String): Result<RepositoryInfo> {
        return try {
            val response = api.getRepository(namespace, repository)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getImageTags(
        namespace: String,
        repository: String,
        page: Int? = null,
        pageSize: Int? = null,
    ): Result<DockerTagsResponse> {
        return try {
            val response = api.getRepositoryTags(namespace, repository, page, pageSize)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun getAllCollections(): Flow<List<CollectionEntity>> {
        return collectionDao.getAllCollections()
    }

    fun getCollectionWithImages(id: Long): Flow<CollectionWithImages> {
        return collectionDao.getCollectionWithImages(id)
    }

    suspend fun createCollection(name: String, description: String, iconName: String) {
        val entity = CollectionEntity(
            name = name,
            description = description,
            iconName = iconName,
            isSystem = false,
            isFavorite = false
        )
        collectionDao.insertCollection(entity)
    }

    suspend fun toggleFavorite(image: DockerImageEntity) {
        val existing = imageDao.getImage(image.name, image.namespace)
        val isFav = existing?.isFavorite ?: false
        val updated = image.copy(isFavorite = !isFav)
        imageDao.insert(updated)
    }

    suspend fun addImageToCollection(image: DockerImageEntity, collectionId: Long) {
        imageDao.insert(image)
        val crossRef = CollectionImageCrossRef(collectionId, image.name)
        collectionDao.insertCollectionImageCrossRef(crossRef)
    }
}
