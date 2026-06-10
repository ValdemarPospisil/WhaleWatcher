package com.valdemar.whalewatcher.data.local

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.valdemar.whalewatcher.data.local.dao.CollectionDao
import com.valdemar.whalewatcher.data.local.dao.ImageDao
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.io.File

@RunWith(RobolectricTestRunner::class)
class DatabasePrepopulatorTest {

    private lateinit var database: AppDatabase
    private lateinit var collectionDao: CollectionDao
    private lateinit var imageDao: ImageDao
    private lateinit var prepopulator: DatabasePrepopulator

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(
            context,
            AppDatabase::class.java
        ).allowMainThreadQueries().build()
        
        collectionDao = database.collectionDao()
        imageDao = database.imageDao()
        prepopulator = DatabasePrepopulator(context, collectionDao, imageDao)
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun prepopulate_insertsCategoriesAndImages() = runTest {
        prepopulator.prepopulate()

        val collections = collectionDao.getAllCollections().first()
        assertTrue(collections.isNotEmpty())
        
        val databasesCategory = collections.find { it.name == "Databases" }
        assertTrue(databasesCategory != null)
        assertEquals("Storage", databasesCategory?.iconName)
        assertEquals(true, databasesCategory?.isSystem)

        val categoryWithImages = collectionDao.getCollectionWithImages(databasesCategory!!.id).first()
        assertTrue(categoryWithImages.images.isNotEmpty())
        
        val postgres = categoryWithImages.images.find { it.name == "postgres" }
        assertTrue(postgres != null)
        assertEquals("library", postgres?.namespace)
    }
}
