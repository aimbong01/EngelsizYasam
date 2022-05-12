package com.engelsizyasam.data.remote

import com.engelsizyasam.data.remote.dto.NewsResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface SeriesDetailApi {

    @GET("playlistItems")
    suspend fun getSeriesDetail(
        @Query("pageToken") pageToken: String?,
        @Query("maxResults") maxResults: String = "50",
        @Query("part") part: String = "snippet",
        @Query("playlistId") playlistId: String,
        @Query("key") key: String = "AIzaSyCkX2pR0YevcbtLB5s6qcUFBYp3m_lfsLU"

    ): NewsResponseDto
}