package com.valdemar.whalewatcher.data.local

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.valdemar.whalewatcher.data.local.dao.CollectionDao
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class DatabasePrepopulationTest {

    private lateinit var database: AppDatabase
    private lateinit var collectionDao: CollectionDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        )
            .addCallback(AppDatabase.PrepopulateCallback()) // We will implement this
            .allowMainThreadQueries()
            .build()
        
        collectionDao = database.collectionDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun verifySystemCollectionsPrepopulated() = runTest {
        // Force database creation
        database.openHelper.writableDatabase
        
        val collections = collectionDao.getAllCollections().first()
        
        val favorites = collections.find { it.isFavorite }
        assertTrue("Favorites collection should exist", favorites != null)
        
        val systemCategories = collections.filter { it.isSystem }
        assertTrue("System categories should be prepopulated", systemCategories.isNotEmpty())
    }
}
