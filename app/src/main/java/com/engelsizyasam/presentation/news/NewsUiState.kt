package com.engelsizyasam.presentation.news

import com.engelsizyasam.domain.model.News


data class NewsUiState(
    val isLoading: Boolean = false,
    val news: List<News> = emptyList(),
    val error: String = ""
)
