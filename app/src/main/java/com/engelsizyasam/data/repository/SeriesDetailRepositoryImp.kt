package com.engelsizyasam.data.repository

import com.engelsizyasam.data.remote.SeriesDetailApi
import com.engelsizyasam.data.remote.dto.NewsResponseDto
import com.engelsizyasam.domain.repository.SeriesDetailRepository
import javax.inject.Inject

class SeriesDetailRepositoryImp @Inject constructor(
    private val api: SeriesDetailApi
) : SeriesDetailRepository {

    override suspend fun getSeriesDetail(pageToken :String, playlistId :String): NewsResponseDto {
        return api.getSeriesDetail(pageToken = pageToken, playlistId = playlistId)
    }
}