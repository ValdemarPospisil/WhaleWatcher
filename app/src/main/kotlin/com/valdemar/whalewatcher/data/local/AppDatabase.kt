package com.valdemar.whalewatcher.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.valdemar.whalewatcher.data.local.dao.CollectionDao
import com.valdemar.whalewatcher.data.local.dao.ImageDao
import com.valdemar.whalewatcher.data.local.entities.CollectionEntity
import com.valdemar.whalewatcher.data.local.entities.CollectionImageCrossRef
import com.valdemar.whalewatcher.data.local.entities.DockerImageEntity

@Database(
    entities = [
        DockerImageEntity::class,
        CollectionEntity::class,
        CollectionImageCrossRef::class
    ],
    version = 3,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun imageDao(): ImageDao
    abstract fun collectionDao(): CollectionDao

    class PrepopulateCallback : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            // Pre-populate system collections
            db.execSQL("INSERT INTO collections (name, description, iconName, is_system, is_favorite) VALUES ('Favorites', 'Your favorite images', 'Favorite', 1, 1)")
            db.execSQL("INSERT INTO collections (name, description, iconName, is_system, is_favorite) VALUES ('Databases', 'Database and storage systems', 'Storage', 1, 0)")
            db.execSQL("INSERT INTO collections (name, description, iconName, is_system, is_favorite) VALUES ('Web Servers', 'Web and proxy servers', 'Language', 1, 0)")
            db.execSQL("INSERT INTO collections (name, description, iconName, is_system, is_favorite) VALUES ('Operating Systems', 'Base OS images', 'Computer', 1, 0)")
            db.execSQL("INSERT INTO collections (name, description, iconName, is_system, is_favorite) VALUES ('Programming Languages', 'Language runtimes and SDKs', 'Code', 1, 0)")
        }
    }

    companion object {
        const val DATABASE_NAME = "whalewatcher.db"
    }
}
