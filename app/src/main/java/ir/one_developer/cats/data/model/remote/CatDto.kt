package ir.one_developer.cats.data.model.remote

import ir.one_developer.cats.data.model.local.CatEntity
import kotlinx.serialization.Serializable

@Serializable
data class CatDto(
    val id: String,
    val url: String,
    val width: Int,
    val height: Int
)

fun CatDto.toCatEntity() = CatEntity(
    id = this.id,
    imageUrl = this.url
)