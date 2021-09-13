package com.engelsizyasam.network

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

interface ContentApiService {

    @GET("playlistItems")
    suspend fun getProperties(
        @Query("pageToken") pageToken: String?,
        @Query("maxResults") maxResults: String,
        @Query("part") part: String,
        @Query("playlistId") playlistId: String,
        @Query("key") key: String = api

    ): SeriesModel
}

object ContentApi {
    val retrofitService: ContentApiService = retrofit.create(ContentApiService::class.java)
}
