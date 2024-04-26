package ir.one_developer.cats.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import ir.one_developer.cats.data.model.local.toCat
import ir.one_developer.cats.data.source.CatDataSource
import ir.one_developer.cats.domain.model.Cat
import ir.one_developer.cats.domain.repository.CatRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

const val PAGE_SIZE = 10

class CatRepositoryImpl(
    private val local: CatDataSource.Local,
    private val remoteMediator: CatRemoteMediator
) : CatRepository {
    @OptIn(ExperimentalPagingApi::class)
    override fun getCats(): Flow<PagingData<Cat>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false,
                initialLoadSize = PAGE_SIZE * 2,
                prefetchDistance = PAGE_SIZE / 2
            ),
            remoteMediator = remoteMediator,
            pagingSourceFactory = { local.cats() }
        ).flow.map { pagingData ->
            pagingData.map { it.toCat() }
        }
    }
}