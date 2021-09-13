package com.engelsizyasam.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SeriesViewModel : ViewModel() {

    private val _navigateToSeriesDetail = MutableLiveData<String?>()
    val navigateToSeriesDetail
        get() = _navigateToSeriesDetail

    fun onSeriesClicked(id: String) {
        _navigateToSeriesDetail.value = id
    }

    fun onSeriesDetailNavigated() {
        _navigateToSeriesDetail.value = null
    }
}