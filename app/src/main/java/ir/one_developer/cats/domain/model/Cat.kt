package ir.one_developer.cats.domain.model

import ir.one_developer.cats.data.model.local.CatEntity

data class Cat(
    val id: String,
    val imageUrl: String,
    val bookmarked : Boolean = false
)

fun Cat.toCatEntity() = CatEntity(
    id = id,
    imageUrl = imageUrl,
    bookmarked = bookmarked
)

fun createCats(size: Int = 20) = buildList {
    repeat(size) {
        val cat = Cat(
            id = it.toString(),
            imageUrl = "https://cdn2.thecatapi.com/images/vq.png"
        )
        add(cat)
    }
}