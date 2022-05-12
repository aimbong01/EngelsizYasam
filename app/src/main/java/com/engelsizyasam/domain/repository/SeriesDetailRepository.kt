package com.engelsizyasam.domain.repository

import com.engelsizyasam.data.remote.dto.NewsResponseDto

interface SeriesDetailRepository {

    suspend fun getSeriesDetail(pageToken :String, playlistId :String): NewsResponseDto
}