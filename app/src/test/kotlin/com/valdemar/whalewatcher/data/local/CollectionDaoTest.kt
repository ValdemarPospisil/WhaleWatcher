package com.valdemar.whalewatcher.data.local

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.valdemar.whalewatcher.data.local.dao.CollectionDao
import com.valdemar.whalewatcher.data.local.entities.CollectionEntity
import com.valdemar.whalewatcher.data.local.entities.CollectionImageCrossRef
import com.valdemar.whalewatcher.data.local.entities.DockerImageEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class CollectionDaoTest {

    private lateinit var database: AppDatabase
    private lateinit var collectionDao: CollectionDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()
        
        collectionDao = database.collectionDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertAndGetCollection() = runTest {
        val collection = CollectionEntity(
            name = "My Collection",
            description = "A custom collection",
            iconName = "List",
            isSystem = false,
            isFavorite = false
        )
        
        val id = collectionDao.insertCollection(collection)
        val loaded = collectionDao.getCollectionById(id)
        
        assertEquals("My Collection", loaded?.name)
        assertEquals("A custom collection", loaded?.description)
    }

    @Test
    fun updateCollection() = runTest {
        val collection = CollectionEntity(
            name = "Old Name",
            description = "Desc",
            iconName = "List",
            isSystem = false,
            isFavorite = false
        )
        val id = collectionDao.insertCollection(collection)
        
        val updated = collection.copy(id = id, name = "New Name")
        collectionDao.updateCollection(updated)
        
        val loaded = collectionDao.getCollectionById(id)
        assertEquals("New Name", loaded?.name)
    }

    @Test
    fun deleteCollection() = runTest {
        val collection = CollectionEntity(
            name = "To Delete",
            description = "Desc",
            iconName = "List",
            isSystem = false,
            isFavorite = false
        )
        val id = collectionDao.insertCollection(collection)
        val loaded = collectionDao.getCollectionById(id)
        assertTrue(loaded != null)
        
        collectionDao.deleteCollection(loaded!!)
        
        val afterDelete = collectionDao.getCollectionById(id)
        assertTrue(afterDelete == null)
    }

    @Test
    fun mapImageToCollection() = runTest {
        val image = DockerImageEntity(
            namespace = "library",
            name = "nginx",
            description = "Nginx web server",
            pullCount = "1000",
            stars = 500,
            isFavorite = false
        )
        database.imageDao().insert(image)
        
        val collection = CollectionEntity(
            name = "Web Servers",
            description = "Servers",
            iconName = "List",
            isSystem = false,
            isFavorite = false
        )
        val collectionId = collectionDao.insertCollection(collection)
        
        val crossRef = CollectionImageCrossRef(
            collectionId = collectionId,
            imageName = "nginx"
        )
        collectionDao.insertCollectionImageCrossRef(crossRef)
        
        val collectionWithImages = collectionDao.getCollectionWithImages(collectionId).first()
        assertEquals(1, collectionWithImages.images.size)
        assertEquals("nginx", collectionWithImages.images[0].name)
    }
}
