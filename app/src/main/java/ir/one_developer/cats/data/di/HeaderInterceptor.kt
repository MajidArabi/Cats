package ir.one_developer.cats.data.di

import ir.one_developer.cats.BuildConfig
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject

class HeaderInterceptor @Inject constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        val newRequestBuilder = request.newBuilder()
        newRequestBuilder.addHeader("x-api-key", BuildConfig.API_KEY)
        return chain.proceed(newRequestBuilder.build())
    }

}