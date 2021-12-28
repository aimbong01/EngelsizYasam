package com.engelsizyasam.network

import com.engelsizyasam.model.NewsModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val api = ""

interface NewsApiService {

    @GET("top-headlines")
    suspend fun getProperties(
        @Query("country") country: String = "tr",
        @Query("apiKey") apiKey: String = api

    ): NewsModel
}
