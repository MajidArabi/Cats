package ir.one_developer.cats.data.db

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import ir.one_developer.cats.data.model.local.CatRemoteKeyEntity

@Dao
interface CatRemoteKeyDao {

    @Upsert
    suspend fun upsertRemoteKey(key: List<CatRemoteKeyEntity>)

    @Query("SELECT * FROM cats_remote_keys WHERE cat_id=:id")
    suspend fun getRemoteKeyByCatId(id: String?): CatRemoteKeyEntity?

    @Query("DELETE FROM cats_remote_keys")
    suspend fun clearRemoteKeys()

}