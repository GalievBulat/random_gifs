package com.kakadurf.randomgifs.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

const val DB_NAME = "cacheDb"

@Database(
    entities = [CachedGif::class],
    version = 1
)
abstract class CacheDb : RoomDatabase() {
    companion object {
        private lateinit var instance: CacheDb

        @Synchronized
        fun create(context: Context): CacheDb {
            if (!::instance.isInitialized) {
                instance = Room.databaseBuilder(
                    context,
                    CacheDb::class.java,
                    DB_NAME
                ).build()
            }
            return instance
        }
    }

    abstract fun getCacheDao(): DBCacheDao
}
