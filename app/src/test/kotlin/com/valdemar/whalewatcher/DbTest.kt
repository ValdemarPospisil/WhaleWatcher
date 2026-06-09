package com.valdemar.whalewatcher

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.valdemar.whalewatcher.data.local.AppDatabase
import com.valdemar.whalewatcher.data.local.entities.DockerImageEntity
import com.valdemar.whalewatcher.data.repository.DockerImageRepository
import com.valdemar.whalewatcher.data.network.DockerHubApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [33])
class DbTest {
    private lateinit var db: AppDatabase
    private lateinit var repository: DockerImageRepository

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .addCallback(AppDatabase.PrepopulateCallback())
            .allowMainThreadQueries()
            .build()
        // Mock api
        val api = object : DockerHubApi {
            override suspend fun searchRepositories(query: String, page: Int?) = TODO()
            override suspend fun getPublicRepositories(namespace: String) = TODO()
            override suspend fun getRepository(namespace: String, repository: String) = TODO()
            override suspend fun getRepositoryTags(namespace: String, repository: String, page: Int?, pageSize: Int?) = TODO()
        }
        repository = DockerImageRepository(api, db.imageDao(), db.collectionDao())
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun testDbFlow(): Unit = runBlocking {
        // Trigger DB creation by querying something
        val collections = repository.getAllCollections().first()
        println("Collections count: ${collections.size}")
        collections.forEach {
            println("Collection: ${it.name}, isFav: ${it.isFavorite}, isSystem: ${it.isSystem}")
        }
        
        val filtered = collections.filter { it.isFavorite || !it.isSystem }
        println("Filtered count: ${filtered.size}")
        
        val favCollection = db.collectionDao().getFavoritesCollection()
        println("Fav collection from DAO: ${favCollection?.name}")

        val img = DockerImageEntity("nginx", "library", "desc", 100, "1M", false, 0)
        repository.toggleFavorite(img)

        val collectionsWithImages = repository.getAllCollectionsWithImages().first()
        val favWithImages = collectionsWithImages.firstOrNull { it.collection.isFavorite }
        println("Fav images count: ${favWithImages?.images?.size}")
    }
}
