package com.engelsizyasam.data.repository

import com.engelsizyasam.domain.repository.NewsRepository
import com.engelsizyasam.data.remote.NewsApi
import com.engelsizyasam.data.remote.SeriesApi
import com.engelsizyasam.data.remote.dto.NewsResponseDto
import com.engelsizyasam.domain.repository.SeriesRepository
import javax.inject.Inject

class SeriesRepositoryImp @Inject constructor(
    private val api: SeriesApi
) : SeriesRepository {

    override suspend fun getSeries(pageToken: String): NewsResponseDto {
        return api.getSeries(pageToken = pageToken)
    }
}