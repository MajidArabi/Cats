package ir.one_developer.cats.data.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cache_timeout")
data class CacheTimeoutEntity(
    @PrimaryKey(autoGenerate = false)
    val timeout : Long
)