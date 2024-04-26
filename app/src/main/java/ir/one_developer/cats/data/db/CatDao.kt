package ir.one_developer.cats.data.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import ir.one_developer.cats.data.model.local.CatEntity

@Dao
interface CatDao {

    @Query("SELECT * FROM cats")
    fun cats(): PagingSource<Int, CatEntity>

    @Upsert
    suspend fun upsertCats(entities: List<CatEntity>)

    @Query("DELETE FROM cats")
    suspend fun clearAll()

}