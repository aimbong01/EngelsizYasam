package com.engelsizyasam.data.remote.dto


data class NewsResponseDto(
    val status: String,
    val totalResults: Int,
    val articles: List<NewsModelDto>
)
