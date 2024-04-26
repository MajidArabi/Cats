package ir.one_developer.cats.data.db

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import ir.one_developer.cats.data.model.local.CacheTimeoutEntity

@Dao
interface CatCacheTimeoutDao {

    @Upsert
    suspend fun upsertCacheTimeout(timeout : CacheTimeoutEntity)

    @Query("SELECT * FROM cache_timeout LIMIT 1")
    suspend fun getCacheTimeout(): CacheTimeoutEntity?

    @Query("DELETE FROM cache_timeout")
    suspend fun clearCacheTimeout()

}