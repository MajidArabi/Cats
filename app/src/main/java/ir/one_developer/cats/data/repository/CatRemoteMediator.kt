package ir.one_developer.cats.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import ir.one_developer.cats.data.model.local.CacheTimeoutEntity
import ir.one_developer.cats.data.model.local.CatEntity
import ir.one_developer.cats.data.model.local.CatRemoteKeyEntity
import ir.one_developer.cats.data.model.remote.toCatEntity
import ir.one_developer.cats.data.source.CatDataSource
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.TimeUnit

private const val STARTING_PAGE_INDEX = 0

@OptIn(ExperimentalPagingApi::class)
class CatRemoteMediator(
    private val local: CatDataSource.Local,
    private val remote: CatDataSource.Remote
) : RemoteMediator<Int, CatEntity>() {

    override suspend fun initialize(): InitializeAction {
        val cacheTimeout = TimeUnit.MILLISECONDS.convert(1, TimeUnit.HOURS)
        val timeout = local.getCacheTimeout()?.timeout ?: 0

        return if (System.currentTimeMillis() - timeout < cacheTimeout) {
            InitializeAction.SKIP_INITIAL_REFRESH
        } else {
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, CatEntity>
    ): MediatorResult {

        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextPage?.minus(1) ?: STARTING_PAGE_INDEX
            }

            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevPage = remoteKeys?.prevPage
                prevPage
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
            }

            LoadType.APPEND  -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextPage = remoteKeys?.nextPage
                nextPage
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
            }
        }

        return try {
            val response = remote.getCats(page = page)

            if (response.isFailure) {
                return MediatorResult.Error(response.exceptionOrNull()!!)
            }

            val items = response.getOrNull().orEmpty()
            val endOfPaginationReached = items.isEmpty()
            val entities = items.map { it.toCatEntity().copy(page = page) }

            if (loadType == LoadType.REFRESH) {
                local.clearCats()
                local.clearRemoteKeys()
                local.clearCacheTimeout()
            }

            val prevPage = if (page == STARTING_PAGE_INDEX) null else page - 1
            val nextPage = if (endOfPaginationReached) null else page + 1

            val remoteKeys = items.map {
                CatRemoteKeyEntity(
                    catId = it.id,
                    currentPage = page,
                    prevPage = prevPage,
                    nextPage = nextPage
                )
            }

            local.save(entities)
            local.saveRemoteKeys(remoteKeys)
            local.saveCacheTimeout(CacheTimeoutEntity(System.currentTimeMillis()))

            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)

        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, CatEntity>
    ): CatRemoteKeyEntity? = state.anchorPosition?.let { position ->
        state.closestItemToPosition(position)?.id?.let { id ->
            local.getRemoteKeyByCatId(id)
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, CatEntity>
    ): CatRemoteKeyEntity? = state.pages.firstOrNull {
        it.data.isNotEmpty()
    }?.data?.firstOrNull()?.let { entity ->
        local.getRemoteKeyByCatId(id = entity.id)
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, CatEntity>
    ): CatRemoteKeyEntity? = state.pages.lastOrNull {
        it.data.isNotEmpty()
    }?.data?.lastOrNull()?.let { entity ->
        local.getRemoteKeyByCatId(id = entity.id)
    }

}