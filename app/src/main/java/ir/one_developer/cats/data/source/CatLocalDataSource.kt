package ir.one_developer.cats.data.source

import androidx.paging.PagingSource
import ir.one_developer.cats.data.db.CatCacheTimeoutDao
import ir.one_developer.cats.data.db.CatDao
import ir.one_developer.cats.data.db.CatRemoteKeyDao
import ir.one_developer.cats.data.di.IoDispatcher
import ir.one_developer.cats.data.model.local.CacheTimeoutEntity
import ir.one_developer.cats.data.model.local.CatEntity
import ir.one_developer.cats.data.model.local.CatRemoteKeyEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class CatLocalDataSource(
    private val catDao: CatDao,
    private val remoteKeyDao: CatRemoteKeyDao,
    private val catCacheTimeoutDao: CatCacheTimeoutDao,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : CatDataSource.Local {

    override fun cats(): PagingSource<Int, CatEntity> = catDao.cats()

    override fun bookmarkedCats(): Flow<List<CatEntity>> = catDao.bookmarkedCats()

    override suspend fun bookmarkCat(
        entity: CatEntity
    ) = withContext(dispatcher) {
        catDao.bookmarkCat(entity)
    }

    override suspend fun save(
        entities: List<CatEntity>
    ) = withContext(dispatcher) {
        catDao.upsertCats(entities)
    }

    override suspend fun getRemoteKeyByCatId(
        id: String?
    ): CatRemoteKeyEntity? = withContext(dispatcher) {
        remoteKeyDao.getRemoteKeyByCatId(id = id)
    }

    override suspend fun saveRemoteKeys(
        key: List<CatRemoteKeyEntity>
    ) = withContext(dispatcher) {
        remoteKeyDao.upsertRemoteKey(key)
    }

    override suspend fun clearCats() = withContext(dispatcher) {
        catDao.clearAll()
    }

    override suspend fun clearRemoteKeys() = withContext(dispatcher) {
        remoteKeyDao.clearRemoteKeys()
    }

    override suspend fun clearCacheTimeout() {
        catCacheTimeoutDao.clearCacheTimeout()
    }

    override suspend fun saveCacheTimeout(timeoutEntity: CacheTimeoutEntity) {
        catCacheTimeoutDao.upsertCacheTimeout(timeoutEntity)
    }

    override suspend fun getCacheTimeout(): CacheTimeoutEntity? {
        return catCacheTimeoutDao.getCacheTimeout()
    }

}