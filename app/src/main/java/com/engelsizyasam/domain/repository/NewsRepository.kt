package com.engelsizyasam.domain.repository

import com.engelsizyasam.data.remote.dto.NewsResponseDto

interface NewsRepository {

    suspend fun getNews(): NewsResponseDto
}