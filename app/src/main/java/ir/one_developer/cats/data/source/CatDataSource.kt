package ir.one_developer.cats.data.source

import androidx.paging.PagingSource
import ir.one_developer.cats.data.model.local.CacheTimeoutEntity
import ir.one_developer.cats.data.model.local.CatEntity
import ir.one_developer.cats.data.model.local.CatRemoteKeyEntity
import ir.one_developer.cats.data.model.remote.CatDto
import kotlinx.coroutines.flow.Flow

interface CatDataSource {

    interface Remote {
        suspend fun getCats(page: Int): Result<List<CatDto>>
    }

    interface Local {
        fun cats(): PagingSource<Int, CatEntity>
        fun bookmarkedCats(): Flow<List<CatEntity>>
        suspend fun bookmarkCat(entity: CatEntity)
        suspend fun save(entities: List<CatEntity>)
        suspend fun getRemoteKeyByCatId(id : String?) : CatRemoteKeyEntity?
        suspend fun saveRemoteKeys(key: List<CatRemoteKeyEntity>)
        suspend fun clearCats()
        suspend fun clearRemoteKeys()
        suspend fun clearCacheTimeout()
        suspend fun saveCacheTimeout(timeoutEntity: CacheTimeoutEntity)
        suspend fun getCacheTimeout() : CacheTimeoutEntity?
    }

}