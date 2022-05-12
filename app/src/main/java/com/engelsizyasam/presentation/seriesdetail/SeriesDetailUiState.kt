package com.engelsizyasam.presentation.seriesdetail

import com.engelsizyasam.domain.model.SeriesDetail


data class SeriesDetailUiState(
    val isLoading: Boolean = false,
    val seriesDetail: List<SeriesDetail> = emptyList(),
    val error: String = ""
)
