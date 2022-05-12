package com.engelsizyasam.data.remote

import com.engelsizyasam.data.remote.dto.NewsResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("top-headlines")
    suspend fun getNews(
        @Query("country") country: String = "tr",
        @Query("apiKey") apiKey: String = "6423607ac7794685b2974458e8815934"
    ): NewsResponseDto
}