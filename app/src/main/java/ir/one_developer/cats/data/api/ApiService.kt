package ir.one_developer.cats.data.api

import ir.one_developer.cats.data.model.remote.CatDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    interface V1 {

        @GET("v1/images/search")
        suspend fun fetchCats(
            @Query("page") page : Int,
            @Query("limit") limit: Int = 10
        ): Response<List<CatDto>>

    }

}