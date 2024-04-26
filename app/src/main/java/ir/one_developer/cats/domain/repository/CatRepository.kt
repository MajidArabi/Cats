package ir.one_developer.cats.domain.repository

import androidx.paging.PagingData
import ir.one_developer.cats.domain.model.Cat
import kotlinx.coroutines.flow.Flow

interface CatRepository {

    fun getCats() : Flow<PagingData<Cat>>

}