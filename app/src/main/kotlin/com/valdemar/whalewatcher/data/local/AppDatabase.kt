package com.valdemar.whalewatcher.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.valdemar.whalewatcher.data.local.dao.ImageDao
import com.valdemar.whalewatcher.data.local.entities.ImageEntity

@Database(entities = [ImageEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun imageDao(): ImageDao
}
