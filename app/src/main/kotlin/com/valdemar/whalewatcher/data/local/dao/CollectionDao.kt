package com.valdemar.whalewatcher.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.valdemar.whalewatcher.data.local.entities.CollectionEntity
import com.valdemar.whalewatcher.data.local.entities.CollectionImageCrossRef
import com.valdemar.whalewatcher.data.local.entities.CollectionWithImages
import kotlinx.coroutines.flow.Flow

@Dao
interface CollectionDao {
    @Query("SELECT * FROM collections ORDER BY is_system DESC, name ASC")
    fun getAllCollections(): Flow<List<CollectionEntity>>

    @Query("SELECT * FROM collections WHERE id = :id LIMIT 1")
    suspend fun getCollectionById(id: Long): CollectionEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCollection(collection: CollectionEntity): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCollections(collections: List<CollectionEntity>)

    @Update
    suspend fun updateCollection(collection: CollectionEntity)

    @Delete
    suspend fun deleteCollection(collection: CollectionEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCollectionImageCrossRef(crossRef: CollectionImageCrossRef)

    @Delete
    suspend fun deleteCollectionImageCrossRef(crossRef: CollectionImageCrossRef)

    @Transaction
    @Query("SELECT * FROM collections WHERE id = :id")
    fun getCollectionWithImages(id: Long): Flow<CollectionWithImages>
}
