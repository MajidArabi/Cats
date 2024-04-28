package ir.one_developer.cats.data.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import ir.one_developer.cats.domain.model.Cat

@Entity(tableName = "cats")
data class CatEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val imageUrl: String?,
    val page : Int? = null,
    val bookmarked : Boolean = false
)

fun CatEntity.toCat() = Cat(
    id = this.id,
    bookmarked = this.bookmarked,
    imageUrl = this.imageUrl.orEmpty()
)