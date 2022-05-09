package com.engelsizyasam.presentation.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.engelsizyasam.common.Resource
import com.engelsizyasam.domain.use_case.get_news.GetNewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(private val getNewsUseCase: GetNewsUseCase) : ViewModel() {

    private val _uiState = MutableStateFlow(NewsUiState())
    val uiState: StateFlow<NewsUiState> = _uiState

    init {
        getNews()
    }

    private fun getNews() {
        getNewsUseCase().onEach { result ->
            when(result) {
                is Resource.Success -> {
                    _uiState.value = NewsUiState(news = result.data ?: emptyList())
                }
                is Resource.Error -> {
                    _uiState.value = NewsUiState(error = result.message ?: "An unexpected error occured.")
                }
                is Resource.Loading -> {
                    _uiState.value = NewsUiState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}