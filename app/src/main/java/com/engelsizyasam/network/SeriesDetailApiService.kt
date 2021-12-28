package com.engelsizyasam.network

import com.engelsizyasam.model.SeriesDetailModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val api = ""


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