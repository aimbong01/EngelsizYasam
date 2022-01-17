package com.engelsizyasam.network

import com.engelsizyasam.model.ScholarModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://serpapi.com/"
private const val api = "b3eda36f567a6039f98b712ed6baed57d3d3a7d627453b82afd3db1be16cde88"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface ScholarApiService {

    @GET("search.json")
    suspend fun getProperties(
        @Query("start") start: String,
        @Query("engine") engine: String = "google_scholar",
        @Query("q") q: String = "engelli+bireyler",
        @Query("api_key") api_key: String = api
    ): ScholarModel
}

object ScholarApi {
    val retrofitService: ScholarApiService = retrofit.create(ScholarApiService::class.java)
}