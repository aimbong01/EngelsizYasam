package com.engelsizyasam.presentation.series

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.engelsizyasam.common.Resource
import com.engelsizyasam.domain.use_case.get_series.GetSeriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class SeriesViewModel @Inject constructor(private val getSeriesUseCase: GetSeriesUseCase) : ViewModel() {

    private val _uiState = MutableStateFlow(SeriesUiState())
    val uiState: StateFlow<SeriesUiState> = _uiState

    init {
        getSeries()
    }

    private fun getSeries() {
        getSeriesUseCase().onEach { result ->
            when(result) {
                is Resource.Success -> {
                    _uiState.value = SeriesUiState(series = result.data ?: emptyList())
                }
                is Resource.Error -> {
                    _uiState.value = SeriesUiState(error = result.message ?: "An unexpected error occured.")
                }
                is Resource.Loading -> {
                    _uiState.value = SeriesUiState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

}

