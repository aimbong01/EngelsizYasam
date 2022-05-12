package com.engelsizyasam.presentation.seriesdetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.engelsizyasam.common.Resource
import com.engelsizyasam.domain.use_case.get_series_detail.GetSeriesDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class SeriesDetailViewModel @Inject constructor(savedStateHandle: SavedStateHandle,private val getSeriesDetailUseCase: GetSeriesDetailUseCase) : ViewModel() {

    private val _seriesName = savedStateHandle.get<String>("seriesName").toString()
    val seriesName: String
        get() = _seriesName

    private var playlistId = savedStateHandle.get<String>("playlistId").toString()

    private val _uiState = MutableStateFlow(SeriesDetailUiState())
    val uiState: StateFlow<SeriesDetailUiState> = _uiState

    init {
        getSeriesDetail()
    }

    private fun getSeriesDetail() {
        getSeriesDetailUseCase.playlistId = playlistId
        getSeriesDetailUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _uiState.value = SeriesDetailUiState(seriesDetail = result.data ?: emptyList())
                }
                is Resource.Error -> {
                    _uiState.value = SeriesDetailUiState(error = result.message ?: "An unexpected error occured.")
                }
                is Resource.Loading -> {
                    _uiState.value = SeriesDetailUiState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

}
