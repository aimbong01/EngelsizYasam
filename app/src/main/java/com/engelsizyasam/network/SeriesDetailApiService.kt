package com.engelsizyasam.network

import com.engelsizyasam.domain.model.SeriesDetailModel
import retrofit2.http.GET
import retrofit2.http.Query

private const val api = "AIzaSyCkX2pR0YevcbtLB5s6qcUFBYp3m_lfsLU"


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