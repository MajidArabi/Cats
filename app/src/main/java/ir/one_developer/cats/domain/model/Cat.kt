package ir.one_developer.cats.domain.model

data class Cat(
    val id: String,
    val imageUrl: String
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