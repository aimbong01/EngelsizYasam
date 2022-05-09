package com.engelsizyasam.domain.repository

import com.engelsizyasam.data.remote.dto.NewsResponseDto

interface SeriesRepository {

    suspend fun getSeries(pageToken: String): NewsResponseDto
}