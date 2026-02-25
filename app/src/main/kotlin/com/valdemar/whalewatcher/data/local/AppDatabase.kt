package com.valdemar.whalewatcher.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.valdemar.whalewatcher.data.local.dao.ImageDao
import com.valdemar.whalewatcher.data.local.entities.DockerImageEntity

@Database(entities = [DockerImageEntity::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun imageDao(): ImageDao

    companion object {
        const val DATABASE_NAME = "whalewatcher.db"
    }
}
