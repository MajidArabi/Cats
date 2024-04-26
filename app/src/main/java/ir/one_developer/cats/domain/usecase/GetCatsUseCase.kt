package ir.one_developer.cats.domain.usecase

import androidx.paging.PagingData
import ir.one_developer.cats.domain.model.Cat
import ir.one_developer.cats.domain.repository.CatRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCatsUseCase @Inject constructor(
    private val catRepository: CatRepository
) {
    operator fun invoke() : Flow<PagingData<Cat>> = catRepository.getCats()
}