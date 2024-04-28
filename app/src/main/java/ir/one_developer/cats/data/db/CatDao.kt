package ir.one_developer.cats.data.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import ir.one_developer.cats.data.model.local.CatEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CatDao {

    @Query("SELECT * FROM cats")
    fun cats(): PagingSource<Int, CatEntity>

    @Query("SELECT * FROM cats WHERE bookmarked=1")
    fun bookmarkedCats(): Flow<List<CatEntity>>

    @Upsert
    suspend fun upsertCats(entities: List<CatEntity>)

    @Upsert
    suspend fun bookmarkCat(entity: CatEntity)

    @Query("DELETE FROM cats")
    suspend fun clearAll()

}