package ir.one_developer.cats.data.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ir.one_developer.cats.BuildConfig
import ir.one_developer.cats.data.db.AppDatabase
import ir.one_developer.cats.data.db.CatCacheTimeoutDao
import ir.one_developer.cats.data.db.CatDao
import ir.one_developer.cats.data.db.CatRemoteKeyDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CacheModule {

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "${BuildConfig.APPLICATION_ID}.db"
        ).build()
    }

    @Provides
    fun provideCatDao(appDatabase: AppDatabase): CatDao {
        return appDatabase.catDao
    }

    @Provides
    fun provideCatRemoteKeyDao(appDatabase: AppDatabase): CatRemoteKeyDao {
        return appDatabase.catRemoteKeyDao
    }

    @Provides
    fun provideCatCacheTimeoutDao(appDatabase: AppDatabase): CatCacheTimeoutDao {
        return appDatabase.catCacheTimeoutDao
    }

}