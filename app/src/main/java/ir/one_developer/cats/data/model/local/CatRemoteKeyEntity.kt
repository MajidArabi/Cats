package ir.one_developer.cats.data.model.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cats_remote_keys")
data class CatRemoteKeyEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "cat_id")
    val catId: String,
    val prevPage: Int?,
    val nextPage: Int?,
    val currentPage : Int
)