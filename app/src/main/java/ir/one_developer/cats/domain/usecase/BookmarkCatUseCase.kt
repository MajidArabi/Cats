package ir.one_developer.cats.domain.usecase

import ir.one_developer.cats.domain.model.Cat
import ir.one_developer.cats.domain.repository.CatRepository
import javax.inject.Inject

class BookmarkCatUseCase @Inject constructor(
    private val catRepository: CatRepository
) {
    suspend operator fun invoke(cat: Cat) {
        catRepository.bookmarkCat(cat)
    }
}