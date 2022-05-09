package com.engelsizyasam.data.repository

import com.engelsizyasam.domain.repository.NewsRepository
import com.engelsizyasam.data.remote.NewsApi
import com.engelsizyasam.data.remote.dto.NewsResponseDto
import javax.inject.Inject

class NewsRepositoryImp @Inject constructor(
    private val api: NewsApi
) : NewsRepository {

    override suspend fun getNews(): NewsResponseDto {
        return api.getNews()
    }
}