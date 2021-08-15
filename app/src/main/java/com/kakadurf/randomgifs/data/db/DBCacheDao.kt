package com.kakadurf.randomgifs.data.db
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface DBCacheDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveToDB(entity: CachedGif): Long

    @Query("select * from cache where id = :id;")
    suspend fun pullFromDB(id: String): CachedGif

    @Query("select * from cache;")
    fun pullAllFromDB(): Flow<List<CachedGif>>

    @Query("select COUNT(id) from cache;")
    suspend fun getDbSize(): Int

    @Delete
    suspend fun deleteFromDb(entity: CachedGif): Int
}
