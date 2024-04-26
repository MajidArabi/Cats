package ir.one_developer.cats.data.source

import ir.one_developer.cats.data.api.ApiService
import ir.one_developer.cats.data.model.remote.CatDto
import javax.inject.Inject

class CatRemoteDataSource @Inject constructor(
    private val apiService: ApiService.V1
) : CatDataSource.Remote {

    override suspend fun getCats(page: Int): Result<List<CatDto>> = try {
        val result = apiService.fetchCats(page = page)
        val errorBody = "Http Error Code ${result.code()}"
        if (result.isSuccessful && result.body() != null) {
            Result.success(result.body()!!)
        } else {
            Result.failure(Exception(errorBody))
        }
    } catch (e: Exception) {
        Result.failure(e)
    }

}