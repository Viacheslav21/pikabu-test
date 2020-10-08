package com.pikabu.test.data.network


import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitManager(baseUrl: String) {

    private val retrofit = retrofitBuilder(baseUrl).build()

    private fun retrofitBuilder(baseUrl: String) = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .client(httpClient())


    private fun httpClient() = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
        .addInterceptor { chain -> setRequestHeaders(chain) }
        .build()

    private fun setRequestHeaders(chain: Interceptor.Chain): Response {
        val original = chain.request()

        val request = original.newBuilder()
            .header("Content-Type", "application/*+json")
            .header("Accept", "application/*+json")
            .method(original.method, original.body)
            .build()
        return chain.proceed(request)
    }

    fun <T> getService(apiClass: Class<T>): T {
        return retrofit.create(apiClass)
    }
}

