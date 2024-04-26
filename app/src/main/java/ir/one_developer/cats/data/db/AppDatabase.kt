package ir.one_developer.cats.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ir.one_developer.cats.data.model.local.CacheTimeoutEntity
import ir.one_developer.cats.data.model.local.CatEntity
import ir.one_developer.cats.data.model.local.CatRemoteKeyEntity

@Database(
    version = 1,
    exportSchema = false,
    entities = [CatEntity::class, CatRemoteKeyEntity::class , CacheTimeoutEntity::class]
)
abstract class AppDatabase : RoomDatabase() {
    abstract val catDao: CatDao
    abstract val catRemoteKeyDao: CatRemoteKeyDao
    abstract val catCacheTimeoutDao: CatCacheTimeoutDao
}