package com.engelsizyasam.presentation.series

import com.engelsizyasam.domain.model.Series


data class SeriesUiState(
    val isLoading: Boolean = false,
    val series: List<Series> = emptyList(),
    val error: String = ""
)
