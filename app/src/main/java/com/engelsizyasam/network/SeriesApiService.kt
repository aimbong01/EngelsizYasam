package com.engelsizyasam.network

import com.engelsizyasam.domain.model.Series
import retrofit2.http.GET
import retrofit2.http.Query


interface SeriesApiService {

    @GET("playlists")
    suspend fun getProperties(
        @Query("part") part: String = "snippet",
        @Query("pageToken") pageToken: String,
        @Query("channelId") channelId: String = "UCgfrxC70niIPNBOqQO2g-IQ",
        @Query("key") key: String = ""

    ): Series
}
