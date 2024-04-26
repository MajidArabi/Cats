package ir.one_developer.cats.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ir.one_developer.cats.data.api.ApiService
import ir.one_developer.cats.data.db.CatCacheTimeoutDao
import ir.one_developer.cats.data.db.CatDao
import ir.one_developer.cats.data.db.CatRemoteKeyDao
import ir.one_developer.cats.data.repository.CatRemoteMediator
import ir.one_developer.cats.data.repository.CatRepositoryImpl
import ir.one_developer.cats.data.source.CatDataSource
import ir.one_developer.cats.data.source.CatLocalDataSource
import ir.one_developer.cats.data.source.CatRemoteDataSource
import ir.one_developer.cats.domain.repository.CatRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideCatRepository(
        local: CatDataSource.Local,
        catRemoteMediator: CatRemoteMediator
    ): CatRepository {
        return CatRepositoryImpl(
            local = local,
            remoteMediator = catRemoteMediator
        )
    }

    @Provides
    @Singleton
    fun provideCatLocalDataSource(
        catDao: CatDao,
        catRemoteKeyDao: CatRemoteKeyDao,
        catCacheTimeoutDao: CatCacheTimeoutDao,
        @IoDispatcher dispatcher: CoroutineDispatcher,
    ): CatDataSource.Local {
        return CatLocalDataSource(
            catDao = catDao,
            dispatcher = dispatcher,
            remoteKeyDao = catRemoteKeyDao,
            catCacheTimeoutDao = catCacheTimeoutDao
        )
    }

    @Provides
    @Singleton
    fun provideCatMediator(
        localDataSource: CatDataSource.Local,
        remoteDataSource: CatDataSource.Remote
    ): CatRemoteMediator {
        return CatRemoteMediator(
            local = localDataSource,
            remote = remoteDataSource
        )
    }

    @Provides
    @Singleton
    fun provideCatRemoteDataSource(
        api: ApiService.V1
    ): CatDataSource.Remote {
        return CatRemoteDataSource(apiService = api)
    }

}