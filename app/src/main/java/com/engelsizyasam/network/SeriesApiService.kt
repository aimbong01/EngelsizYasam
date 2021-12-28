package com.engelsizyasam.network

import com.engelsizyasam.model.SeriesModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val api = "AIzaSyCkX2pR0YevcbtLB5s6qcUFBYp3m_lfsLU"

interface SeriesApiService {

    @GET("playlists")
    suspend fun getProperties(
        @Query("part") part: String = "snippet",
        @Query("pageToken") pageToken: String,
        @Query("channelId") channelId: String = "UCgfrxC70niIPNBOqQO2g-IQ",
        @Query("key") key: String = api

    ): SeriesModel
}
