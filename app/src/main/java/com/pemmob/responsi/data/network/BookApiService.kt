package com.pemmob.responsi.data.network

import com.pemmob.responsi.data.model.SearchResponse
import com.pemmob.responsi.util.Constants
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface BookApiService {
    @GET("search.json")
    suspend fun searchBooks(
        @Query("q") query: String,
        @Query("limit") limit: Int
    ): SearchResponse
}

object ApiClient {
    // OpenLibrary kadang lambat, jadi timeout dinaikkan
    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .build()

    val instance: BookApiService by lazy {
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BookApiService::class.java)
    }
}