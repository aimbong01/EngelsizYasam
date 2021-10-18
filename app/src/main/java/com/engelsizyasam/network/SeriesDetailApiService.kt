package com.engelsizyasam.network

import com.engelsizyasam.model.SeriesDetailModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://www.googleapis.com/youtube/v3/"
private const val api = ""

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface SeriesDetailApiService {

    @GET("playlistItems")
    suspend fun getProperties(
        @Query("pageToken") pageToken: String?,
        @Query("maxResults") maxResults: String = "50",
        @Query("part") part: String = "snippet",
        @Query("playlistId") playlistId: String,
        @Query("key") key: String = api

    ): SeriesDetailModel
}

object SeriesDetailApi {
    val retrofitService: SeriesDetailApiService = retrofit.create(SeriesDetailApiService::class.java)
}
