package com.valdemar.whalewatcher.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.valdemar.whalewatcher.data.local.entities.DockerImageEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ImageDao {

    @Query("SELECT * FROM docker_images WHERE is_favorite = 1 ORDER BY last_updated DESC")
    fun getFavorites(): Flow<List<DockerImageEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(image: DockerImageEntity)

    @Delete
    suspend fun delete(image: DockerImageEntity)

    @Query("SELECT EXISTS(SELECT * FROM docker_images WHERE name = :name AND namespace = :namespace AND is_favorite = 1)")
    suspend fun isFavorite(name: String, namespace: String): Boolean

    @Query("SELECT * FROM docker_images WHERE name = :name AND namespace = :namespace LIMIT 1")
    suspend fun getImage(name: String, namespace: String): DockerImageEntity?
}
